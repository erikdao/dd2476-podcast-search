package dd2476.group18.podcastsearch.models;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Tells Lomdok to create getter and setter for all attributes
@AllArgsConstructor // Tells Lomdok to create constructor with all attributes
@NoArgsConstructor // Tells Lomdok to create constructor to no attribute
@Table(name = "episodes")
public class Episode {
    @Id
    @Column(name = "episode_uri")
    private String episodeUri;

    @Column(name = "episode_name")
    private String episodeName;

    @Column(name = "episode_description")
    private String episodeDescription;

    @Column(name = "duration")
    private Double duration;
    
    @Column(name = "episode_filename_prefix")
    private String episodeFileNamePrefix;

    @ManyToOne
    @JoinColumn(name = "show_uri", foreignKey = @ForeignKey(name = "episodes_show_uri_fkey"))
    private Show show;

    @Column(name = "transcript", columnDefinition = "TEXT", nullable = true)
    private String transcript;

    @Type(type = "jsonb")
    @Column(name = "word_tokens", columnDefinition = "jsonb", nullable = true)
    @Basic(fetch = FetchType.EAGER)   
    private List<WordToken> wordTokens;
}