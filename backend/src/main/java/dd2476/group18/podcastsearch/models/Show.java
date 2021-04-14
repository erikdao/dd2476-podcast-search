package dd2476.group18.podcastsearch.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shows")
public class Show {
    @Id
    @Column(name = "show_uri")
    private String showUri;

    @Column(name = "show_name")
    private String showName;

    @Column(name = "show_description")
    private String showDescription;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "rss_link")
    private String rssLink;

    @Column(name = "show_filenam_prefix")
    private String showFileNamePrefix;

    public Show(String showUri, String showName) {
        this.showUri = showUri;
        this.showName = showName;
    }

    public String getShowUri() {
        return showUri;
    }

    public void setShowUri(String showUri) {
        this.showUri = showUri;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowDescription() {
        return showDescription;
    }

    public void setShowDescription(String showDescription) {
        this.showDescription = showDescription;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getShowFileNamePrefix() {
        return showFileNamePrefix;
    }

    public void setShowFileNamePrefix(String showFileNamePrefix) {
        this.showFileNamePrefix = showFileNamePrefix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((rssLink == null) ? 0 : rssLink.hashCode());
        result = prime * result + ((showDescription == null) ? 0 : showDescription.hashCode());
        result = prime * result + ((showFileNamePrefix == null) ? 0 : showFileNamePrefix.hashCode());
        result = prime * result + ((showName == null) ? 0 : showName.hashCode());
        result = prime * result + ((showUri == null) ? 0 : showUri.hashCode());
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
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (rssLink == null) {
            if (other.rssLink != null)
                return false;
        } else if (!rssLink.equals(other.rssLink))
            return false;
        if (showDescription == null) {
            if (other.showDescription != null)
                return false;
        } else if (!showDescription.equals(other.showDescription))
            return false;
        if (showFileNamePrefix == null) {
            if (other.showFileNamePrefix != null)
                return false;
        } else if (!showFileNamePrefix.equals(other.showFileNamePrefix))
            return false;
        if (showName == null) {
            if (other.showName != null)
                return false;
        } else if (!showName.equals(other.showName))
            return false;
        if (showUri == null) {
            if (other.showUri != null)
                return false;
        } else if (!showUri.equals(other.showUri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Show [showUri=" + showUri + ", showName=" + showName + "]";
    }
}
