package dd2476.group18.podcastsearch.models;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "episodes")
public class ElasticEpisode implements Serializable {
    @Id
    private String id;

    private double duration;

    @Field(name = "episode_uri")
    private String episodeUri;

    @Field(name = "episode_name")
    private String episodeName;

    @Field(name = "episode_description")
    private String episodeDescription;

    @Field(name = "show_uri")
    private String showUri;

    private String transcript;

    public String getId() {
        return id;
    }

    public String getEpisodeUri() {
        return episodeUri;
    }

    public double getDuration() {
        return duration;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getShowUri() {
        return showUri;
    }

    public String getTranscript() {
        return transcript;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEpisodeUri(String episodeUri) {
        this.episodeUri = episodeUri;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public void setEpisodeDescription(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }

    public void setShowUri(String showUri) {
        this.showUri = showUri;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }
}
