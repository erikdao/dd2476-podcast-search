package dd2476.group18.podcastsearch.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.service.ShowService;
import dd2476.group18.podcastsearch.views.View;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {
    @Autowired
    private final ShowService showService;

    @GetMapping
    @JsonView(View.List.class)
    public List<Show> getAllShows(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "15") Integer pageSize,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        return showService.getAllShows(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    @JsonView(View.Detail.class)
    public Show getShowById(@PathVariable(name = "id") String id) {
        return showService.getShowById(id);
    }
}
