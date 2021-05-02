package dd2476.group18.podcastsearch.searchers;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * A class representing a segment from highlights in Elasticsearch results
 */
@RequiredArgsConstructor
public class HighlightSegment {
    /*
     * All normalized (i.e., lowercase, remove punctuations) tokens in this segment
     */
    private List<String> normalizedTokens;

    /* Indice of the highlighted token in this segment */
    private List<Integer> highlightIndices;
}
