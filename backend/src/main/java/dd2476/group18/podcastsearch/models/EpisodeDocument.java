package dd2476.group18.podcastsearch.models;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@Document(indexName = "episodes", createIndex = true)
public class EpisodeDocument implements Serializable {
    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "duration", type = FieldType.Double)
    private double duration;

    @Field(name = "episode_uri")
    private String episodeUri;

    @Field(name = "episode_name", type = FieldType.Text)
    private String episodeName;

    @Field(name = "episode_description", type = FieldType.Text)
    private String episodeDescription;

    @Field(name = "show_uri", type = FieldType.Keyword)
    private String showUri;

    @Field(name = "transcript", type = FieldType.Text)
    private String transcript;
}
