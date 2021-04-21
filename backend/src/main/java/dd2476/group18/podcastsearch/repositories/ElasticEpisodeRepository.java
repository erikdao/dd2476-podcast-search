package dd2476.group18.podcastsearch.repositories;

import dd2476.group18.podcastsearch.models.ElasticEpisode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElasticEpisodeRepository extends ElasticsearchRepository<ElasticEpisode, String> {
    ElasticEpisode findByEpisodeUri(String uri);

    ElasticEpisode findByEpisodeId(String id);
}
