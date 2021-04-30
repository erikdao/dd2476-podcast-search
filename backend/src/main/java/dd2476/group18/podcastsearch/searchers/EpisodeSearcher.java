package dd2476.group18.podcastsearch.searchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.service.EpisodeDocumentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EpisodeSearcher {
    @Autowired
    private final EpisodeDocumentService episodeDocumentService;
    @Autowired
    private final EpisodeRepository episodeRepository;

    public List<Episode> searchEpisodeByTranscript(String query) {
        // Step 1: search with elastic search
        List<MatchedEpisodeDocument> episodeDocuments = new ArrayList<>();
        try {
            episodeDocuments = episodeDocumentService.phraseTranscriptSearch(query);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> episodeIds = episodeDocuments
            .stream()
            .map(MatchedEpisodeDocument::getEpisodeId)
            .collect(Collectors.toList());

        List<Episode> episodes = episodeRepository.findByIdIn(episodeIds);
        return episodes;
    }
}
