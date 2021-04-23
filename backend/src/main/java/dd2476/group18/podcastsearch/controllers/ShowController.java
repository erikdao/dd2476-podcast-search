package dd2476.group18.podcastsearch.controllers;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShowController {
    final ShowRepository showRepository;

    @GetMapping("/shows/{id}")
    public Show findShowById(@PathVariable("id") String id) {
        return showRepository.findById(id).get();
    }
}
