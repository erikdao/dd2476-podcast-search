package dd2476.group18.podcastsearch.importers;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.models.Transcript;
import dd2476.group18.podcastsearch.repositories.EpisodeDocumentRepository;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@RequiredArgsConstructor
@Slf4j
public class TranscriptLoader {

    @Autowired
    private final EpisodeRepository episodeRepository;
    private final EpisodeDocumentRepository episodeDocumentRepository;

    public AlternativeResultBean loadTranscriptFromJson(String jsonPath) {
        return this.loadTranscriptFromJson(new File(jsonPath));
    }

    public AlternativeResultBean loadTranscriptFromJson(File jsonFile) {
        final ObjectMapper objectMapper = new ObjectMapper();
        AlternativeResultBean results = new AlternativeResultBean();

        try {
            results = objectMapper.readValue(jsonFile, new TypeReference<AlternativeResultBean>(){});
        } catch (JsonParseException e) {
            log.error("JsonParseException " + e.getMessage());
        } catch (JsonMappingException e) {
            log.error("JsonMappingException " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException " + e.getMessage());
        }

        return results;
    }

    public void persistTranscript(AlternativeResultBean results, String episodeId) {
        Optional<Episode> episodeRS = episodeRepository.findById(episodeId);
        if (!episodeRS.isPresent()) {
            log.error("persistTranscript: episode id=" + episodeId + " not found");
            return;
        }
        Episode episode = episodeRS.get();

        results.combineTranscriptsAndWords();
        persistTranscriptToDb(results, episode);
        updateEpisodeDocumentTranscript(results, episode);
        results.reset();
    }

    private void persistTranscriptToDb(AlternativeResultBean results, Episode episode) { 
        Transcript transcript = Transcript.builder()
            .episode(episode)
            .transcript(results.getTranscripts())
            .wordTokens(results.buildWordTokenList())
            .build();
        episode.setTranscript(transcript);
        episodeRepository.save(episode);
    }

    private void updateEpisodeDocumentTranscript(AlternativeResultBean results, Episode episode) { 
        Optional<EpisodeDocument> epDocRS = episodeDocumentRepository.findById(episode.getId());
        if (!epDocRS.isPresent()) {
            log.error("update Episode document transcript: episode id=" + episode.getId() + " not found");
        }

        EpisodeDocument doc = epDocRS.get();
        doc.setTranscript(episode.getTranscript().getTranscript());
        episodeDocumentRepository.save(doc);
    }
}
