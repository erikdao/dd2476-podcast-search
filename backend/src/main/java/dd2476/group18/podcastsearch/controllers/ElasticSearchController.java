package dd2476.group18.podcastsearch.controllers;

import dd2476.group18.podcastsearch.models.ElasticEpisode;
import dd2476.group18.podcastsearch.models.ElasticShow;
import dd2476.group18.podcastsearch.repositories.ElasticEpisodeRepository;
import dd2476.group18.podcastsearch.repositories.ElasticShowRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ElasticSearchController {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticEpisodeRepository elasticEpisodeRepository;
    private final ElasticShowRepository elasticShowRepository;

    public ElasticSearchController(ElasticsearchOperations elasticsearchOperations, ElasticEpisodeRepository elasticEpisodeRepository, ElasticShowRepository elasticShowRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticEpisodeRepository = elasticEpisodeRepository;
        this.elasticShowRepository = elasticShowRepository;
    }

    // ---------------- for searching episodes ----------------
    @GetMapping("/episode/id/{id}")
    public ElasticEpisode findEpisodeById(@PathVariable("id") String id) {
        return elasticEpisodeRepository.findByEpisodeId(id);
    }

    @GetMapping("/episode/uri/{uri}")
    public ElasticEpisode findEpisodeByUri(@PathVariable("uri") String uri) {
        return elasticEpisodeRepository.findByEpisodeUri(uri);
    }

    @GetMapping("/search/episode/description/{query}")
    public List<ElasticEpisode> searchEpisodeByDescription(@PathVariable("query") String s) {
        Criteria criteria = new Criteria("episode_description").contains(s);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ElasticEpisode> searchHits = elasticsearchOperations.search(query, ElasticEpisode.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @GetMapping("/search/episode/transcript/{query}")
    public List<ElasticEpisode> searchEpisodeByTranscript(@PathVariable("query") String s) {
        Criteria criteria = new Criteria("transcript").contains(s);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ElasticEpisode> searchHits = elasticsearchOperations.search(query, ElasticEpisode.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    // ---------------- for searching shows ----------------
    @GetMapping("/show/id/{id}")
    public ElasticShow findShowById(@PathVariable("id") String id) {
        return elasticShowRepository.findByShowId(id);
    }

    @GetMapping("/show/uri/{uri}")
    public ElasticShow findShowByUri(@PathVariable("uri") String uri) {
        return elasticShowRepository.findByShowUri(uri);
    }
}