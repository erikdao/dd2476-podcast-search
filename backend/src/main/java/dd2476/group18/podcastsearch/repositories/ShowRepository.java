package dd2476.group18.podcastsearch.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dd2476.group18.podcastsearch.models.Show;

@Repository
public interface ShowRepository extends PagingAndSortingRepository<Show, String> {
    List<Show> findByShowUri(String showUri);
}
