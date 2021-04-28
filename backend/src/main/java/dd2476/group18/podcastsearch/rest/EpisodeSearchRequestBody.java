package dd2476.group18.podcastsearch.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EpisodeSearchRequestBody {
    /* Search query term */
    private String query;

    /* Type of search, e.g., phrase or multi-word */
    private String type;
}
