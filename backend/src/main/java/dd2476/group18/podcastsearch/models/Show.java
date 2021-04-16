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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shows")
public class Show {
    @Id
    private String id;

    @Column(name = "show_uri")
    private String showUri;

    @Column(name = "show_name")
    private String showName;

    @Column(name = "show_description")
    private String showDescription;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "language")
    private String language;

    @Column(name = "rss_link")
    private String rssLink;

    @Column(name = "show_filenam_prefix")
    private String showFileNamePrefix;
}
