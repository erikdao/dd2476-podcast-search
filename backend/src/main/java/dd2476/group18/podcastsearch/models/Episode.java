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

import dd2476.group18.podcastsearch.searchers.HighlightSegment;
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
    private List<EpisodeClip> clips;

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

    public EpisodeClip buildClipForTerms(HighlightSegment highlightSegment, int clipLength) {
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
            token -> token.getWord().toLowerCase().replaceAll("[\\.,;!\\?]", "")
        ).collect(Collectors.toList());

        // Remove <em></em> in some words in the highlightSegment
        List<String> normalizedTokens = highlightSegment.getNormalizedTokens()
                    .stream().map(t -> t.replaceAll("</em>", "").replaceAll("<em>", ""))
                    .collect(Collectors.toList());

        // Match the highlight chunk to allTokens to find the start index of the clip
        int startIndex = Collections.indexOfSubList(allTokens, normalizedTokens);

        // Annotate hightlight
        highlightSegment.getHighlightIndices().forEach(index -> {
            this.wordTokens.get(index + startIndex).setHighlight(true);
        });
    
        // Build the clip
        WordToken startWord = this.wordTokens.get(startIndex);

        /* To build a clip, we look to the first word of each phrase and take 3 token backs (if possible),
         * this is to create a friendly clip. Then we'll take all the words whose startTime is less than
         * or equal to firstWord.startTime + clipLength (defatul to 60). Along the way, we can annotate
         * which words should be highlighted (as they're appreared in the phrase)
         */ 
        List<WordToken> wordsInClip = this.wordTokens.stream()
        .filter(token -> {
            double startTime = token.getStartTime();
            return (startTime >= startWord.getStartTime()) && (startTime <= startWord.getStartTime() + clipLength);
        })
        .collect(Collectors.toList());

        EpisodeClip clip = EpisodeClip.builder()
            .order(0)
            .startTime(wordsInClip.get(0).getStartTime())
            .endTime(wordsInClip.get(wordsInClip.size() - 1).getEndTime())
            .wordTokens(wordsInClip)
            .build();

        return clip;
    }
}
