package dd2476.group18.podcastsearch.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import dd2476.group18.podcastsearch.searchers.QueryTerms;
import dd2476.group18.podcastsearch.views.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "episodes", indexes = {
    @Index(columnList = "episode_uri"),
    @Index(columnList = "show_id")
})
public class Episode {
    @Id
    @Column(name = "id", length = 50)
    @JsonView(View.Minimal.class)
    private String id;

    @Column(name = "episode_uri")
    @JsonView(View.Minimal.class)
    private String episodeUri;

    @Column(name = "episode_name")
    @JsonView(View.Minimal.class)
    private String episodeName;

    @Column(name = "episode_description", columnDefinition = "TEXT")
    @JsonView(View.List.class)
    private String episodeDescription;

    @Column(name = "duration")
    @JsonView(View.List.class)
    private double duration;

    @Column(name = "episode_filename_prefix")
    private String episodeFilenamePrefix;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "show_id", nullable = false)
    @JsonView(View.List.class)
    private Show show;

    @OneToOne(mappedBy = "episode", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Transcript transcript;

    // Properties used for search result
    @JsonView(View.List.class)
    @Transient
    private float score;

    @JsonIgnore
    @Transient  // So that this property is not persisted to the database
    private List<WordToken> wordTokens;

    @JsonView(View.List.class)
    @Transient  // So that this property is not persisted to the database
    private List<EpisodeClip> clips = new ArrayList<>();

    @Override
    public String toString() {
        return "Episode [EpisodeUri=" + episodeUri + ", EpisodeName=" + episodeName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Episode other = (Episode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public EpisodeDocument createEpisodeDocument() {
        return EpisodeDocument.builder()
            .id(this.getId())
            .episodeName(this.getEpisodeName())
            .episodeUri(this.getEpisodeUri())
            .episodeDescription(this.getEpisodeDescription())
            .duration(this.getDuration())
            .transcript(this.getTranscript().getTranscript())
            .showUri(this.getShow().getShowUri())
            .build();
    }
    public EpisodeDocument createEpisodeDocument(boolean includeTranscript) {
        if (!includeTranscript) {
            return EpisodeDocument.builder()
                .id(this.getId())
                .episodeName(this.getEpisodeName())
                .episodeUri(this.getEpisodeUri())
                .episodeDescription(this.getEpisodeDescription())
                .duration(this.getDuration())
                .showUri(this.getShow().getShowUri())
                .build();
        }
        return this.createEpisodeDocument();
    }

    public void buildClipForTerms(QueryTerms terms, int clipLength) {
        if (this.wordTokens == null || this.wordTokens.size() == 0) {
            this.wordTokens = this.getTranscript().getWordTokens();
            Collections.sort(this.wordTokens);
        } else {
            log.debug("wordTokens loaded");
        }

        /*
         * Get a list of all tokens in this episode. We remove all the punctuations
         * from the word for better matching with the `normalizedTerms` below
         */
        List<String> allTokens = this.wordTokens.stream().map(
            token -> token.getWord().toLowerCase().replaceAll("[\\.,;!]", "")
        ).collect(Collectors.toList());

        List<String> normalizedTerms = terms.getTerms().stream().map(
            t -> t.toLowerCase()
        ).collect(Collectors.toList());

        // This is only true for phrase query
        // Find the index of the `terms` which is a sublist of all tokens
        ArrayList<Integer> termsIndices = new ArrayList<>();
        List<String> tempTokens = new ArrayList<>(allTokens);

        int position = Collections.indexOfSubList(tempTokens, normalizedTerms);
        int index = position;
        // TODO: check this algorithm, it's not working probably for 2-word queries
        while (position != -1) {
            termsIndices.add(index);
            try {
                tempTokens = tempTokens.subList(index + normalizedTerms.size(), tempTokens.size());
                position = Collections.indexOfSubList(tempTokens, normalizedTerms);
                if (position != -1) {
                    index = index + normalizedTerms.size() + position;
                }
            } catch (IndexOutOfBoundsException e) {
                position = -1;
            } catch (IllegalArgumentException e) {
                position = -1;
            }
        }

        if (termsIndices.size() == 0 || termsIndices.stream().allMatch(ti -> ti == -1)) {
            this.setClips(new ArrayList<>());
            return;
        }

        // Annotate highlights
        for (Integer termsIndex: termsIndices) {
            for (int i = termsIndex; i < normalizedTerms.size() + termsIndex; i++) {
                WordToken word = this.wordTokens.get(i);
                word.setHighlight(true);
                this.wordTokens.set(i, word);
            }
        }
        /*
         * From the termsIndices list, we know how many instances of the phrase there are in the transcript
         * of the episode. Next, we're going to build clips from those instances.
         * 
         * To build a clip, we look to the first word of each phrase and take 3 token backs (if possible),
         * this is to create a friendly clip. Then we'll take all the words whose startTime is less than
         * or equal to firstWord.startTime + clipLength (defatul to 60). Along the way, we can annotate
         * which words should be highlighted (as they're appreared in the phrase)
         */
        int order = 0;
        for (Integer termsIndex: termsIndices) {
            int startIndex = termsIndex - 3 >= 0 ? termsIndex - 3 : termsIndex;
            WordToken startWord = this.wordTokens.get(startIndex);

            // Get all words that are between the startWord and `clipLength` seconds after that
            List<WordToken> wordsInClip = this.wordTokens.stream()
                .filter(token -> {
                    double startTime = token.getStartTime();
                    return (startTime >= startWord.getStartTime()) && (startTime <= startWord.getStartTime() + clipLength);
                })
                .collect(Collectors.toList());

            EpisodeClip clip = EpisodeClip.builder()
                .order(++order)
                .startTime(wordsInClip.get(0).getStartTime())
                .endTime(wordsInClip.get(wordsInClip.size() - 1).getEndTime())
                .wordTokens(wordsInClip)
                .build();

            this.clips.add(clip);
        }
    }
}
