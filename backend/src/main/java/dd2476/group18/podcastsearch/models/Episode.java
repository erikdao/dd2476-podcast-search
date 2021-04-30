package dd2476.group18.podcastsearch.models;

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

@Entity
@Data
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
    @Transient
    private List<WordToken> wordTokens;

    @JsonView(View.List.class)
    @Transient
    private List<WordToken> clips;

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

    public void buildClipForTerms(QueryTerms terms) {
        if (this.wordTokens == null || this.wordTokens.size() == 0) {
            this.wordTokens = this.getTranscript().getWordTokens();
            Collections.sort(this.wordTokens);
        }

        String firstTerm = terms.getTerms().get(0);
        // Get the first work token corresponding to the term
        WordToken startToken = this.wordTokens.stream()
                                .filter(token -> token.getWord().contains(firstTerm))
                                .findFirst()
                                .get();
        startToken.setHighlight(true);

        // Get all word tokens that are between the startToken and 1 minute after that
        List<WordToken> tokensInClip = this.wordTokens.stream()
            .filter(token -> {
                double startTime = token.getStartTime();
                return (startTime >= startToken.getStartTime() - 1.0) && (startTime <= startToken.getStartTime() + 120.0);
            })
            .map(token -> {
                if (terms.getTerms().contains(token.getWord())) {
                    token.setHighlight(true);
                }
                return token;
            })
            .collect(Collectors.toList());
        Collections.sort(tokensInClip);
        
        this.setClips(tokensInClip);
    }
}
