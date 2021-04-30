package dd2476.group18.podcastsearch.controllers;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.models.ShowDocument;
import dd2476.group18.podcastsearch.service.EpisodeDocumentService;
import dd2476.group18.podcastsearch.service.ShowDocumentService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ElasticSearchController {
    @Autowired
    private final EpisodeDocumentService episodeDocumentService;
    @Autowired
    private final ShowDocumentService showDocumentService;

    // ---------------- for searching episodes ----------------
    @GetMapping("/episode/id/{id}")
    public EpisodeDocument findByEpisodeId(@PathVariable("id") String id) {
        return episodeDocumentService.findByEpisodeId(id);
    }

    @GetMapping("/episode/uri/{uri}")
    public EpisodeDocument findByEpisodeUri(@PathVariable("uri") String uri) {
        return episodeDocumentService.findByEpisodeUri(uri);
    }

    // @PostMapping("/search/episode")
    // public List<EpisodeDocument> searchEpisode(@RequestParam String field,
    //                                            @RequestParam String text,
    //                                            @RequestParam int page,
    //                                            @RequestParam int size,
    //                                            @RequestParam String searchType
    //                                                         ) {
    //     return episodeDocumentService.searchWithMatchQuery(field, text, page, size, searchType);
    // }

    // ---------------- for searching shows ----------------
    @GetMapping("/show/id/{id}")
    public ShowDocument findShowById(@PathVariable("id") String id) {
        return showDocumentService.findById(id);
    }

    @GetMapping("/show/uri/{uri}")
    public ShowDocument findShowByUri(@PathVariable("uri") String uri) {
        return showDocumentService.findByShowUri(uri);
    }
}
