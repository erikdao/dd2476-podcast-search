package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.repositories.EpisodeDocumentRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpisodeDocumentService {
    final String[] includes = new String[]{};
    final String[] excludes = new String[]{"transcripts"};
    final SourceFilter sourceFilter = new FetchSourceFilter(includes, excludes);
    private final ElasticsearchOperations elasticsearchOperations;
    private final EpisodeDocumentRepository episodeDocumentRepository;

    public EpisodeDocumentService(ElasticsearchOperations elasticsearchOperations, EpisodeDocumentRepository episodeDocumentRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.episodeDocumentRepository = episodeDocumentRepository;
    }

    // use elastic search to find a specific episode by id
    public EpisodeDocument findByEpisodeId(String id) {
        return episodeDocumentRepository.findByEpisodeId(id);
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
    public List<EpisodeDocument> searchWithMatchQuery(String fieldName, String text, int page, int size, String searchType) {
        QueryBuilder queryBuilder;
        if (searchType.equals("phrase")) {
            queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, text);
        } else {
            queryBuilder = QueryBuilders.matchQuery(fieldName, text).operator(Operator.AND);
        }
        Query query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(page, size))
                .withSourceFilter(sourceFilter)
                .build();
        SearchHits<EpisodeDocument> searchHits = elasticsearchOperations.search(query, EpisodeDocument.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

}
