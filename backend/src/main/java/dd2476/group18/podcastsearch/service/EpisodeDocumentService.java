package dd2476.group18.podcastsearch.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.repositories.EpisodeDocumentRepository;
import dd2476.group18.podcastsearch.searchers.HighlightSegment;
import dd2476.group18.podcastsearch.searchers.MatchedEpisodeDocument;
import dd2476.group18.podcastsearch.searchers.QueryTerms;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EpisodeDocumentService {
    final String[] includes = new String[]{"id"};
    final String[] excludes = new String[]{};
    final SourceFilter sourceFilter = new FetchSourceFilter(includes, excludes);
    @Autowired
    private final EpisodeDocumentRepository episodeDocumentRepository;

    @Autowired
    private final RestHighLevelClient elasticsearchClient;

    private final SearchRequest searchRequest = new SearchRequest("episodes");

    // use elastic search to find a specific episode by id
    public EpisodeDocument findByEpisodeId(String id) {
        return episodeDocumentRepository.findById(id).get();
    }

    // use elastic search to find a specific episode by uri
    public EpisodeDocument findByEpisodeUri(String uri) {
        return episodeDocumentRepository.findByEpisodeUri(uri);
    }

    public List<MatchedEpisodeDocument> phraseTranscriptSearch(String query) throws IOException {
        // Define a match phrase query, exlude `transcript` field from the result
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("transcript", query));
        sourceBuilder.from(0);
        sourceBuilder.size(15);
        sourceBuilder.fetchSource(includes, excludes);

        // Highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.highlighterType("plain");
        highlightBuilder.fragmenter("simple");
        HighlightBuilder.Field highlightTranscript = new HighlightBuilder.Field("transcript");
        highlightBuilder.field(highlightTranscript);
        sourceBuilder.highlighter(highlightBuilder);
        
        // Pack everything into the search request
        searchRequest.source(sourceBuilder);

        SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();

        List<MatchedEpisodeDocument> results = new ArrayList<>();

        for (SearchHit hit: searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = (String) sourceAsMap.get("id");
            MatchedEpisodeDocument episode = new MatchedEpisodeDocument();
            episode.setEpisodeId(id);
            episode.setScore(hit.getScore());
            
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("transcript");
            Text[] fragments = highlightField.fragments();
            List<HighlightSegment> highlightSegments = new ArrayList<>();

            Arrays.asList(fragments).stream()
                .forEach((Text text) -> {
                    highlightSegments.add(HighlightSegment.builder()
                    .normalizedTokens(text.toString())
                    .highlightIndices()
                    .build());
                });
            
            episode.setHighlightSegments(highlightSegments);
            results.add(episode);
        }

        return results;
    }

}
