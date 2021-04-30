package dd2476.group18.podcastsearch.searchers;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchedEpisodeDocument {
    private String episodeId;
    private List<QueryTerms> queryTerms;
}
