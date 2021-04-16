package dd2476.group18.podcastsearch.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "episodes")
public class Episode {
    @Id
    private String id;

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

    @Override
    public String toString() {
        return "Episode [EpisodeUri=" + episodeUri + ", EpisodeName=" + episodeName + "]";
    }
}
