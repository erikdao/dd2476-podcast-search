package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.ElasticShow;
import dd2476.group18.podcastsearch.repositories.ElasticShowRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class ElasticShowService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticShowRepository elasticShowRepository;

    public ElasticShowService(ElasticsearchOperations elasticsearchOperations, ElasticShowRepository elasticShowRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticShowRepository = elasticShowRepository;
    }

    public ElasticShow findByShowId(String id) {
        return elasticShowRepository.findByShowId(id);
    }

    public ElasticShow findByShowUri(String uri) {
        return elasticShowRepository.findByShowUri(uri);
    }

}
