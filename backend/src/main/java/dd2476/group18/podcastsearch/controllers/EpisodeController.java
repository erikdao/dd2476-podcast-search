package dd2476.group18.podcastsearch.controllers;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.rest.EpisodeSearchRequestBody;
import dd2476.group18.podcastsearch.searchers.EpisodeSearcher;
import dd2476.group18.podcastsearch.views.View;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/episodes")
@RequiredArgsConstructor
public class EpisodeController {
    @Autowired
    private final EpisodeSearcher episodeSearcher;

    @JsonView(View.List.class)
    @PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
    public List<Episode> searchEpisode(
        @RequestParam(name = "from", defaultValue = "0") int from,
        @RequestParam(name = "size", defaultValue = "15") int size,
        @RequestBody EpisodeSearchRequestBody body
    ) {
        System.out.println("Query type: " + body.getType());
        //At tis point in time there is only 2 query types (phrase and multiword)
        if(body.getType().equals("multiword")){
            return episodeSearcher.multiwordSearchEpisodeByTranscript(body.getQuery(), body.getClipLength(), from, size);
        } else {
            return episodeSearcher.phraseSearchEpisodeByTranscript(body.getQuery(), body.getClipLength(), from, size); 
        }
        
    }
}
