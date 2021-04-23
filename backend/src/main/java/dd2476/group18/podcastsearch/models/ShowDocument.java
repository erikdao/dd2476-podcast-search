package dd2476.group18.podcastsearch.models;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@Document(indexName = "shows")
public class ShowDocument implements Serializable {
    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "language")
    private String language;

    @Field(name = "publisher")
    private String publisher;

    @Field(name = "show_name")
    private String showName;

    @Field(name = "show_uri")
    private String showUri;

    @Field(name = "show_description")
    private String showDescription;
}
