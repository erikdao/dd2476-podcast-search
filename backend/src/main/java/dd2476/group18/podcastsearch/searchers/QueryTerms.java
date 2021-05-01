package dd2476.group18.podcastsearch.searchers;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryTerms {
    /** Order of the token set in the highlighted results */
    private Integer order;
    private ArrayList<String> terms;
}
