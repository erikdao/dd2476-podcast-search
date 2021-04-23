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
@Document(indexName = "shows", createIndex = true)
public class ShowDocument implements Serializable {
    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "language", type = FieldType.Keyword)
    private String language;

    @Field(name = "publisher", type = FieldType.Keyword)
    private String publisher;

    @Field(name = "show_name", type = FieldType.Text)
    private String showName;

    @Field(name = "show_uri", type = FieldType.Keyword)
    private String showUri;

    @Field(name = "show_description", type = FieldType.Text)
    private String showDescription;
}
