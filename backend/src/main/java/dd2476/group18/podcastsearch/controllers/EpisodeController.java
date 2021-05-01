package dd2476.group18.podcastsearch.controllers;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.rest.EpisodeSearchRequestBody;
import dd2476.group18.podcastsearch.searchers.EpisodeSearcher;
import dd2476.group18.podcastsearch.views.View;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/episodes")
@RequiredArgsConstructor
public class EpisodeController {
    @Autowired
    private final EpisodeSearcher episodeSearcher;

    @JsonView({ View.List.class })
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
    public List<Episode> searchEpisode(@RequestBody EpisodeSearchRequestBody body) {
        String query = body.getQuery();
        List<Episode> episodes = episodeSearcher.searchEpisodeByTranscript(query, body.getClipLength());
        return episodes;
    }
}
