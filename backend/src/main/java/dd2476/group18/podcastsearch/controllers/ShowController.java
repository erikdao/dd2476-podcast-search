package dd2476.group18.podcastsearch.controllers;

import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShowController {
    final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

}
