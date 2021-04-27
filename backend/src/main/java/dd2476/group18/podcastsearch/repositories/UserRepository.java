package dd2476.group18.podcastsearch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dd2476.group18.podcastsearch.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {}
