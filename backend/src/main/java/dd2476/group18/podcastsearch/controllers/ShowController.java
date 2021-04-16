package dd2476.group18.podcastsearch.controllers;

import javax.websocket.server.PathParam;

import org.elasticsearch.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShowController {
    final ShowRepository showRepository;

    @GetMapping("/search")
    public Show findByUri(@PathParam(value = "show_uri") String showUri) {
        String id = "spotify:show:" + showUri;
        System.out.printf("ShowController /search id = %s \n", id);
        System.out.printf("ShowController num entries: %d \n", showRepository.count());

        //TODO: fix return values for this function, compiler complians about returning an optional, either unpack it here och change return value
        return showRepository.findById("spotify:show:2NYtxEZyYelR6RMKmjfPLB").orElseThrow();
   }
}
