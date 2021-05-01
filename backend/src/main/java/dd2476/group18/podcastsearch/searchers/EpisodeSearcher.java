package dd2476.group18.podcastsearch.searchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public List<Episode> phraseSearchEpisodeByTranscript(String query, int clipLength) {
        // Step 1: search with elastic search
        List<MatchedEpisodeDocument> episodeDocuments = new ArrayList<>();
        try {
            episodeDocuments = episodeDocumentService.phraseTranscriptSearch(query);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Episode> episodes = episodeDocuments
            .stream()
            .map((MatchedEpisodeDocument doc) -> {
                Episode episode = episodeRepository.findById(doc.getEpisodeId()).get();
                episode.setScore(doc.getScore());
                QueryTerms terms = doc.getQueryTerms().get(0);
                episode.buildClipForTerms(terms, clipLength);
                return episode;
            })
            .collect(Collectors.toList());
        return episodes;
    }

    public List<Episode> multiwordSearchEpisodeByTranscript(String query, int clipLength) {
        return new ArrayList<Episode>();
    }

    /**
     * Search for episode transcript given the query, favour full matches by
     * combining multiple type of ES searches
     * @param query
     * @param clipLength
     * @return
     */
    public List<Episode> flexibleSearchEpisodeByTranscript(String query, int clipLength) {
        return new ArrayList<>();
    }
}
