package dd2476.group18.podcastsearch.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dd2476.group18.podcastsearch.models.Episode;

@Repository
public interface EpisodeRepository extends CrudRepository<Episode, String> {
    List<Episode> findByIdIn(@Param("ids") List<String> ids);
}