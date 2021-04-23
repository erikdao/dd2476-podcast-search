package dd2476.group18.podcastsearch.controllers;

import dd2476.group18.podcastsearch.models.ElasticEpisode;
import dd2476.group18.podcastsearch.models.ElasticShow;
import dd2476.group18.podcastsearch.service.ElasticEpisodeService;
import dd2476.group18.podcastsearch.service.ElasticShowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ElasticSearchController {
    private final ElasticEpisodeService elasticEpisodeService;
    private final ElasticShowService elasticShowService;

    public ElasticSearchController(ElasticEpisodeService elasticEpisodeService, ElasticShowService elasticShowService) {
        this.elasticEpisodeService = elasticEpisodeService;
        this.elasticShowService = elasticShowService;
    }

    // ---------------- for searching episodes ----------------
    @GetMapping("/episode/id/{id}")
    public ElasticEpisode findByEpisodeId(@PathVariable("id") String id) {
        return elasticEpisodeService.findByEpisodeId(id);
    }

    @GetMapping("/episode/uri/{uri}")
    public ElasticEpisode findByEpisodeUri(@PathVariable("uri") String uri) {
        return elasticEpisodeService.findByEpisodeUri(uri);
    }

    @PostMapping("/search/episode/description")
    public List<ElasticEpisode> searchEpisodeByDescription(@RequestParam String transcript) {
        String[] words = transcript.split(" ");
        return elasticEpisodeService.searchEpisodeByDescription(words);
    }

    @GetMapping("/search/episode/transcript/{query}")
    public List<ElasticEpisode> searchEpisodeByTranscript(@PathVariable("query") String s) {
        return elasticEpisodeService.searchEpisodeByTranscript(s);
    }

    // ---------------- for searching shows ----------------
    @GetMapping("/show/id/{id}")
    public ElasticShow findShowById(@PathVariable("id") String id) {
        return elasticShowService.findByShowId(id);
    }

    @GetMapping("/show/uri/{uri}")
    public ElasticShow findShowByUri(@PathVariable("uri") String uri) {
        return elasticShowService.findByShowUri(uri);
    }
}
