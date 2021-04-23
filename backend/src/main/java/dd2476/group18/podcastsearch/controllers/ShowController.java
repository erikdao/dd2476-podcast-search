package dd2476.group18.podcastsearch.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.service.ShowService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {
    @Autowired
    private final ShowService showService;

    @GetMapping
    public List<Show> getAllShows(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "15") Integer pageSize,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        return showService.getAllShows(pageNo, pageSize, sortBy);
    }
}
