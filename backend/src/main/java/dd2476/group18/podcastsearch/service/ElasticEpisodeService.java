package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.ElasticEpisode;
import dd2476.group18.podcastsearch.repositories.ElasticEpisodeRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticEpisodeService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticEpisodeRepository elasticEpisodeRepository;

    public ElasticEpisodeService(ElasticsearchOperations elasticsearchOperations, ElasticEpisodeRepository elasticEpisodeRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticEpisodeRepository = elasticEpisodeRepository;
    }

    // use elastic search to find a specific episode by id
    public ElasticEpisode findByEpisodeId(String id) {
        return elasticEpisodeRepository.findByEpisodeId(id);
    }

    // use elastic search to find a specific episode by uri
    public ElasticEpisode findByEpisodeUri(String uri) {
        return elasticEpisodeRepository.findByEpisodeUri(uri);
    }

    // use elastic search to find episodes that contains the given word in their transcripts
    public List<ElasticEpisode> searchEpisodeByTranscript(String s) {
        Criteria criteria = new Criteria("transcript").contains(s);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ElasticEpisode> searchHits = elasticsearchOperations.search(query, ElasticEpisode.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ElasticEpisode> searchEpisodeByDescription(String... words) {
        if (words.length == 1) {
            System.out.println("One word query");
        } else {
            System.out.println("Multi-word query");
        }
        Criteria criteria = new Criteria("description").contains(words[0]).contains(words[1]);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ElasticEpisode> searchHits = elasticsearchOperations.search(query, ElasticEpisode.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
