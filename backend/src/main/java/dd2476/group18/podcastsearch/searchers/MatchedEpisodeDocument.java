package dd2476.group18.podcastsearch.searchers;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchedEpisodeDocument {
    private String episodeId;
    private float score;
    private List<QueryTerms> queryTerms;

    public String getEpisodeId() {
        return episodeId;
    }
}
