package dd2476.group18.podcastsearch.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @Column(name = "episode_uri")
    private String episodeUri;

    @Column(name = "episode_name")
    private String episodeName;

    @Column(name = "episode_description")
    private String episodeDescription;

    @Column(name = "duration")
    private String duration;

    @Column(name = "episode_filename_prefix")
    private String episodeFilenamePrefix;


    public Episode(String episodeUri, String episodeName) {
        this.episodeUri = episodeUri;
        this.episodeName = episodeName;
    }
    public Episode(){
        this.episodeUri = "";
        this.episodeName = "";
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



    public String getEpisodeFileNamePrefix() {
        return episodeFilenamePrefix;
    }

    public void setEpisodeFileNamePrefix(String episodeFileNamePrefix) {
        this.episodeFilenamePrefix = episodeFileNamePrefix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((episodeDescription == null) ? 0 : episodeDescription.hashCode());
        result = prime * result + ((episodeFilenamePrefix == null) ? 0 : episodeFilenamePrefix.hashCode());
        result = prime * result + ((episodeName == null) ? 0 : episodeName.hashCode());
        result = prime * result + ((episodeUri == null) ? 0 : episodeUri.hashCode());
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
        if (episodeDescription == null) {
            if (other.episodeDescription != null)
                return false;
        } else if (!episodeDescription.equals(other.episodeDescription))
            return false;
        if (episodeFilenamePrefix == null) {
            if (other.episodeFilenamePrefix != null)
                return false;
        } else if (!episodeFilenamePrefix.equals(other.episodeFilenamePrefix))
            return false;
        if (episodeName == null) {
            if (other.episodeName != null)
                return false;
        } else if (!episodeName.equals(other.episodeName))
            return false;
        if (episodeUri == null) {
            if (other.episodeUri != null)
                return false;
        } else if (!episodeUri.equals(other.episodeUri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Episode [EpisodeUri=" + episodeUri + ", EpisodeName=" + episodeName + "]";
    }
}
