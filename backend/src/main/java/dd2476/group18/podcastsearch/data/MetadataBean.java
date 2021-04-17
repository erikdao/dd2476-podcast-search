package dd2476.group18.podcastsearch.data;

import com.univocity.parsers.annotations.Parsed;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.Show;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MetadataBean {
    @Parsed(field = "show_uri")
    private String showUri;
    
    @Parsed(field = "show_name")
    private String showName;

    @Parsed(field = "show_description")
    private String showDescription;

    @Parsed(field = "publisher")
    private String publisher;

    @Parsed(field = "language")
    private String language;

    @Parsed(field = "rss_link")
    private String rssLink;

    @Parsed(field = "show_filename_prefix")
    private String showFileNamePrefix;

    @Parsed(field = "episode_uri")
    private String episodeUri;

    @Parsed(field = "episode_name")
    private String episodeName;

    @Parsed(field = "episode_description")
    private String episodeDescription;

    @Parsed(field = "duration")
    private Double duration;

    @Parsed(field = "episode_filename_prefix")
    private String episodeFilenamePrefix;

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

    public String getLanguage() {
        return language;
    }

    @Parsed
    public void setLanguage(String language) {
        this.language = language.replace("['", "").replace("']", "");
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

    public String getEpisodeUri() {
        return episodeUri;
    }

    public void setEpisodeUri(String episodeUri) {
        this.episodeUri = episodeUri;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public void setEpisodeDescription(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getEpisodeFilenamePrefix() {
        return episodeFilenamePrefix;
    }

    public void setEpisodeFilenamePrefix(String episodeFilenamePrefix) {
        this.episodeFilenamePrefix = episodeFilenamePrefix;
    }

    public Show createShowInstance() {
        String id = this.showUri.replace("spotify:show:", "");

        Show show = Show.builder() 
            .id(id)
            .showUri(this.getShowUri())
            .showName(this.getShowName())
            .showDescription(this.getShowDescription())
            .publisher(this.getPublisher())
            .language(this.getLanguage())
            .rssLink(this.getRssLink())
            .showFileNamePrefix(this.getShowFileNamePrefix())
            .build();

        return show;
    }

    public Episode createEpisodeInstance(Show show) {
        String id = this.episodeUri.replace("spotify:episode:", "");

        Episode episode = Episode.builder()
            .id(id)
            .episodeUri(this.getEpisodeUri())
            .episodeName(this.getEpisodeName())
            .episodeDescription(this.getEpisodeDescription())
            .duration(this.getDuration())
            .episodeFilenamePrefix(this.getEpisodeFilenamePrefix())
            .show(show)
            .build();
        
        return episode;
    }
}
