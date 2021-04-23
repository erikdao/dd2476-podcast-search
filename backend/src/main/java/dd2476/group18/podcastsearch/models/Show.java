package dd2476.group18.podcastsearch.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shows", indexes = {
    @Index(columnList = "show_uri")
})
public class Show {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "show_uri")
    private String showUri;

    @Column(name = "show_name")
    private String showName;

    @Column(name = "show_description", columnDefinition = "TEXT")
    private String showDescription;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "language")
    private String language;

    @Column(name = "rss_link")
    private String rssLink;

    @Column(name = "show_filenam_prefix")
    private String showFileNamePrefix;

    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Episode> episodes;

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
        Show other = (Show) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Show [id=" + id + ", showName=" + showName + "]";
    }

    public ShowDocument createShowDocument() {
        return ShowDocument.builder()
            .id(this.getId())
            .showName(this.getShowName())
            .showDescription(this.getShowDescription())
            .showUri(this.getShowUri())
            .language(this.getLanguage())
            .publisher(this.getPublisher())
            .build();
    }
}
