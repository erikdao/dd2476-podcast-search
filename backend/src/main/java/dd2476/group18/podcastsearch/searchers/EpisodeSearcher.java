package dd2476.group18.podcastsearch.searchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.EpisodeClip;
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

    public List<Episode> phraseSearchEpisodeByTranscript(String query, int clipLength, int from, int size) {
        // Step 1: search with elastic search
        List<MatchedEpisodeDocument> episodeDocuments = new ArrayList<>();
        try {
            episodeDocuments = episodeDocumentService.phraseTranscriptSearch(query, from, size);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Episode> episodes = episodeDocuments
            .stream()
            .map((MatchedEpisodeDocument doc) -> {
                Episode episode = episodeRepository.findById(doc.getEpisodeId()).get();
                episode.setScore(doc.getScore());
                episode.setClips(new ArrayList<EpisodeClip>());
                for (HighlightSegment highlightSegment: doc.getHighlightSegments()) {
                    EpisodeClip clip = episode.buildClipForTerms(highlightSegment, clipLength);
                    episode.getClips().add(clip);
                }
                return episode;
            })
            .collect(Collectors.toList());
        return episodes;
    }

    public List<Episode> multiwordSearchEpisodeByTranscript(String query, int clipLength, int from, int size) {
        List<MatchedEpisodeDocument> episodeDocuments = new ArrayList<>();
        try {
            episodeDocuments = episodeDocumentService.multiwordTranscriptSearch(query, from, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
