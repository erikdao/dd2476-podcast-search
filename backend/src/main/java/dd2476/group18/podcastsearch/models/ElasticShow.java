package dd2476.group18.podcastsearch.models;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "shows")
public class ElasticShow implements Serializable {
    @Id
    @Field(name = "id")
    private String showId;

    private String language;

    private String publisher;

    @Field(name = "show_name")
    private String showName;

    @Field(name = "show_uri")
    private String showUri;

    @Field(name = "show_description")
    private String showDescription;

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowUri() {
        return showUri;
    }

    public void setShowUri(String showUri) {
        this.showUri = showUri;
    }

    public String getShowDescription() {
        return showDescription;
    }

    public void setShowDescription(String showDescription) {
        this.showDescription = showDescription;
    }
}
