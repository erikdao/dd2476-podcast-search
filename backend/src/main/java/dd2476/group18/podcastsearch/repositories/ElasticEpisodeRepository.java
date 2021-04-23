package dd2476.group18.podcastsearch.repositories;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ElasticEpisodeRepository extends ElasticsearchRepository<EpisodeDocument, String> {
    EpisodeDocument findByEpisodeUri(String uri);
}
