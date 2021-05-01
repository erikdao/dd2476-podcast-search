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

    // use elastic search to find a specific episode by id
    public EpisodeDocument findByEpisodeId(String id) {
        return episodeDocumentRepository.findById(id).get();
    }

    // use elastic search to find a specific episode by uri
    public EpisodeDocument findByEpisodeUri(String uri) {
        return episodeDocumentRepository.findByEpisodeUri(uri);
    }

    // use elastic search to find episodes that contains the given word in their transcripts

    /**
     *
     * @param fieldName The specified field in the index
     * @param text The sequence of words to search
     * @param page Zero-based page index from which the returned result starts, normally set to 0
     * @param size The size of each page
     * @param searchType Either "phrase" or "match"
     * @return A list of EpisodeDocuments found by ElasticSearch
     */
    // public List<EpisodeDocument> searchWithMatchQuery(String fieldName, String text, int page, int size, String searchType) {
    //     QueryBuilder queryBuilder;
    //     if (searchType.equals("phrase")) {
    //         queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, text);
    //     } else {
    //         queryBuilder = QueryBuilders.matchQuery(fieldName, text).operator(Operator.AND);
    //     }
    //     Query query = new NativeSearchQueryBuilder()
    //             .withQuery(queryBuilder)
    //             .withPageable(PageRequest.of(page, size))
    //             .withSourceFilter(sourceFilter)
    //             .build();
    //     SearchHits<EpisodeDocument> searchHits = elasticsearchOperations.search(query, EpisodeDocument.class);

    //     return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    // }

    public List<MatchedEpisodeDocument> phraseTranscriptSearch(String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest("episodes");
        // Define a match phrase query, exlude `transcript` field from the result
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("transcript", query));
        sourceBuilder.from(0);
        sourceBuilder.size(15);
        sourceBuilder.fetchSource(includes, excludes);

        // Highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.highlighterType("plain");
        HighlightBuilder.Field highlightTranscript = new HighlightBuilder.Field("transcript");
        highlightBuilder.field(highlightTranscript);
        sourceBuilder.highlighter(highlightBuilder);
        
        // Pack everything into the search request
        searchRequest.source(sourceBuilder);

        Instant startSearch = Instant.now();
        SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        Instant receiveESResponse = Instant.now();
        Duration esTime = Duration.between(startSearch, receiveESResponse);
        log.info("Elasticsearch time: " + esTime.toMillis() + " ms");
        SearchHits hits = response.getHits();
        log.info("# of hits: " + hits.getTotalHits());
        SearchHit[] searchHits = hits.getHits();

        List<MatchedEpisodeDocument> results = new ArrayList<>();

        for (SearchHit hit: searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = (String) sourceAsMap.get("id");
            MatchedEpisodeDocument episode = new MatchedEpisodeDocument();
            episode.setEpisodeId(id);
            episode.setScore(hit.getScore());
            episode.setQueryTerms(new ArrayList<>());

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("transcript");
            Text[] fragments = highlightField.fragments();

            int order = 0;
            QueryTerms queryTerms = new QueryTerms();

            for (Text text: fragments) {
                Pattern p = Pattern.compile("(<em>[^\\s]+</em>\\s?)+");
                Matcher matcher = p.matcher(text.toString());
                String highlightText = "";

                while (matcher.find()) {
                    highlightText = matcher.group();
                }
                ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(highlightText.split("\\s")));
                ArrayList<String> tokens = new ArrayList<>(
                    tokenList.stream().map(
                        to -> to.replace("</em>", "").replace("<em>", "")
                    ).collect(Collectors.toList())
                );
                queryTerms.setOrder(++order);
                queryTerms.setTerms(tokens);

                episode.getQueryTerms().add(queryTerms);
            }

            results.add(episode);
        }

        return results;
    }

}
