package dd2476.group18.podcastsearch.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.rest.EpisodeSearchRequestBody;
import dd2476.group18.podcastsearch.service.EpisodeDocumentService;
import dd2476.group18.podcastsearch.views.View;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/episodes")
@RequiredArgsConstructor
public class EpisodeController {
    final EpisodeRepository episodeRepository;
    private final EpisodeDocumentService episodeDocumentService;

    @JsonView(View.List.class)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public List<Episode> searchEpisode(@RequestBody EpisodeSearchRequestBody body) {
        // Step 1: search with elastic search
        try {
            List<EpisodeDocument> episodeDocuments = episodeDocumentService.phraseTranscriptSearch(body.getQuery());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Step 2: Post-process those documents
        return new ArrayList<Episode>();
    }
}
