package dd2476.group18.podcastsearch.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dd2476.group18.podcastsearch.models.Show;

@RepositoryRestResource(collectionResourceRel = "shows", path = "shows")
public interface ShowRepository extends PagingAndSortingRepository<Show, String> {
    List<Show> findByShowUri(String showUri);
    Page<Show> findAll(Pageable pageable);
}
