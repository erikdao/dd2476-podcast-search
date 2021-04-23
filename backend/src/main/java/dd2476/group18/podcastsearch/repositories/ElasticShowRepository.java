package dd2476.group18.podcastsearch.repositories;

import dd2476.group18.podcastsearch.models.ShowDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticShowRepository extends ElasticsearchRepository<ShowDocument, String> {
    ShowDocument findByShowName(String showName);
    ShowDocument findByShowUri(String showUri);
}
