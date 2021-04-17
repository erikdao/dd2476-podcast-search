package dd2476.group18.podcastsearch.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transcripts", indexes = {
    @Index(columnList = "episode_id")
})
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "transcript", columnDefinition = "TEXT", nullable = false)
    private String transcript;

    @Type(type = "jsonb")
    @Column(name = "word_tokens", columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<WordToken> wordTokens;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "episode_id", referencedColumnName = "id")
    private Episode episode;
}
