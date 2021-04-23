package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.repositories.ElasticEpisodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EpisodeDocumentService {
    private final ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private final ElasticEpisodeRepository elasticEpisodeRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * Manually create the `episodes` index
     */
    public void createIndex() {
        elasticsearchRestTemplate.indexOps(EpisodeDocument.class).create();
    }

    // use elastic search to find a specific episode by id
    public EpisodeDocument findByEpisodeId(String id) {
        return elasticEpisodeRepository.findById(id).get();
    }

    // use elastic search to find a specific episode by uri
    public EpisodeDocument findByEpisodeUri(String uri) {
        return elasticEpisodeRepository.findByEpisodeUri(uri);
    }

    // use elastic search to find episodes that contains the given word in their transcripts
    public List<EpisodeDocument> searchEpisodeByTranscript(String s) {
        Criteria criteria = new Criteria("transcript").contains(s);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EpisodeDocument> searchHits = elasticsearchOperations.search(query, EpisodeDocument.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<EpisodeDocument> searchEpisodeByDescription(String... words) {
        if (words.length == 1) {
            log.info("One word query");
        } else {
            log.info("Multi-word query");
        }
        Criteria criteria = new Criteria("description").contains(words[0]).contains(words[1]);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EpisodeDocument> searchHits = elasticsearchOperations.search(query, EpisodeDocument.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
