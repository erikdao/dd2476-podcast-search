package dd2476.group18.podcastsearch.searchers;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryTerms {
    /** Order of the token set in the highlighted results */
    private Integer order;
    private List<String> terms;
}
