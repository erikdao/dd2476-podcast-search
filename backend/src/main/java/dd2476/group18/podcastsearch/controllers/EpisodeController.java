package dd2476.group18.podcastsearch.controllers;

import javax.websocket.server.PathParam;

import org.elasticsearch.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;

@RestController
public class EpisodeController {
    final EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @GetMapping("/search2")
    public Episode findByUri(@PathParam(value = "show_uri") String episodeUri) {
        String id = "spotify:episode:" + episodeUri;
        System.out.printf("EpisodeController /search id = %s \n", id);
        System.out.printf("EpisodeController num entries: %d \n", episodeRepository.count());

        //TODO: fix return values for this function, compiler complians about returning an optional, either unpack it here och change return value
        return episodeRepository.findById("spotify:episode:000A9sRBYdVh66csG2qEdj").orElseThrow(() -> new ResourceNotFoundException("not found :)"));
   }
}
