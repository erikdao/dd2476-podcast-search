package dd2476.group18.podcastsearch.controllers;

import javax.websocket.server.PathParam;

import org.elasticsearch.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShowController {
   final ShowRepository showRepository;

   public Show findByUri(@PathParam(value = "show_uri") String showUri) {
       String id = "spotify:show:" + showUri;
       return null;
        //TODO: fix return values for this function, compiler complians about returning an optional, either unpack it here och change return value
    //    return showRepository.findById(showUri).orElseThrow(ResourceNotFoundException::new);
   }
}
