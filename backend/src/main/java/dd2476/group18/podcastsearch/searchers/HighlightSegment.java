package dd2476.group18.podcastsearch.searchers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * A class representing a segment from highlights in Elasticsearch results
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HighlightSegment {
    /*
     * All normalized (i.e., lowercase, remove punctuations) tokens in this segment
     */
    private List<String> normalizedTokens;

    /* Indice of the highlighted token in this segment */
    private List<Integer> highlightIndices;

    public static class HighlightSegmentBuilder {
        public HighlightSegmentBuilder normalizedTokens(String input) {
            String[] tokens = input.trim().split("\\s");
            this.normalizedTokens = Arrays.asList(tokens).stream()
                .map(s -> s.toLowerCase().replaceAll("[\\.,;!\\?]", ""))
                .collect(Collectors.toList());
            
            return this;
        }

        public HighlightSegmentBuilder highlightIndices() {
            this.highlightIndices = IntStream.range(0, this.normalizedTokens.size())
                .filter(i -> this.normalizedTokens.get(i).contains("<em>"))
                .boxed()
                .collect(Collectors.toList());

            return this;
        }
    }
}
