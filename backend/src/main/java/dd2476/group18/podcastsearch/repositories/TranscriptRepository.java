package dd2476.group18.podcastsearch.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dd2476.group18.podcastsearch.models.Transcript;

@Repository
public interface TranscriptRepository extends CrudRepository<Transcript, Integer> {}
