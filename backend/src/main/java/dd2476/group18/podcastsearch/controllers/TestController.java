package dd2476.group18.podcastsearch.controllers;

import dd2476.group18.podcastsearch.models.ElasticEpisode;
import dd2476.group18.podcastsearch.repositories.ElasticEpisodeRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {
    private final ElasticEpisodeRepository elasticEpisodeRepository;

    public TestController(ElasticEpisodeRepository elasticEpisodeRepository) {
        this.elasticEpisodeRepository = elasticEpisodeRepository;
    }


    @GetMapping("/episode/id/{id}")
    public ElasticEpisode findEpisodeById(@PathVariable("id") String id) {
        Optional<ElasticEpisode> episode = elasticEpisodeRepository.findById(id);

        return episode.get();
    }

    @GetMapping("/episode/uri/{uri}")
    public String searchByUri(@PathVariable("uri") String uri) {
        System.out.println(uri);
        List<ElasticEpisode> elasticEpisodes = elasticEpisodeRepository.findByEpisodeUri(uri);
        return elasticEpisodes.get(0).getEpisodeDescription();
    }
}
