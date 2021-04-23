package dd2476.group18.podcastsearch.models;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@Document(indexName = "episodes")
public class EpisodeDocument implements Serializable {
    @Id
    @Field(name = "id")
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

    @Field(name = "transcript")
    private String transcript;
}
