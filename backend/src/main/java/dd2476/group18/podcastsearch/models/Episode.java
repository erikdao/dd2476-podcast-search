package dd2476.group18.podcastsearch.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "episodes", indexes = {
    @Index(columnList = "episode_uri"),
    @Index(columnList = "show_id")
})
public class Episode {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "episode_uri")
    private String episodeUri;

    @Column(name = "episode_name")
    private String episodeName;

    @Column(name = "episode_description", columnDefinition = "TEXT")
    private String episodeDescription;

    @Column(name = "duration")
    private double duration;

    @Column(name = "episode_filename_prefix")
    private String episodeFilenamePrefix;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Override
    public String toString() {
        return "Episode [EpisodeUri=" + episodeUri + ", EpisodeName=" + episodeName + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpisodeUri() {
        return episodeUri;
    }

    public void setEpisodeUri(String episodeUri) {
        this.episodeUri = episodeUri;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public void setEpisodeDescription(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getEpisodeFilenamePrefix() {
        return episodeFilenamePrefix;
    }

    public void setEpisodeFilenamePrefix(String episodeFilenamePrefix) {
        this.episodeFilenamePrefix = episodeFilenamePrefix;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
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

    
}
