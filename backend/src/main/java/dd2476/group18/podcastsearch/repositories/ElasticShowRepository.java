package dd2476.group18.podcastsearch.repositories;

import dd2476.group18.podcastsearch.models.ElasticShow;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticShowRepository extends ElasticsearchRepository<ElasticShow, String> {
    ElasticShow findByShowName(String showName);
    ElasticShow findByShowId(String showId);
    ElasticShow findByShowUri(String showUri);
}
