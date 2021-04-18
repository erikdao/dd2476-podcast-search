package dd2476.group18.podcastsearch.dataloader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.Transcript;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;

import java.io.BufferedReader;
import java.io.File;

public class TranscriptLoader {
    private static final String DATA_PATH = "/data/podcasts-transcript/spotify-podcasts-2020/";
    private static final String JSON_FILES_PATH = DATA_PATH + "json_file_list.txt";
    private static final String TRANSCRIPT_PATH = DATA_PATH + "podcasts-transcripts/";

    @Autowired
    private final EpisodeRepository episodeRepository;

    private String workingDir;

    public TranscriptLoader(String workingDir, EpisodeRepository episodeRepository) {
        this.workingDir = workingDir;
        this.episodeRepository = episodeRepository;
    }

    public void executePipeline(String... args) {
        String filePath = workingDir + JSON_FILES_PATH;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                // Load and parse transcript from json file
                String jsonPath = workingDir + TRANSCRIPT_PATH + line.strip();
                AlternativeResultBean results = this.loadTranscriptFromJson(jsonPath);

                // Extract episode id;
                String[] pathComponents = line.split("/");
                String episodeId = pathComponents[pathComponents.length - 1].replace(".json", "");

                // Persist the transcript and word tokens of the epsiode to database
                this.persistTranscript(results, episodeId);

                if (count++ % 1000 == 0) {
                    System.out.println("Persisted transcript and tokens for " + count + " episodes");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AlternativeResultBean loadTranscriptFromJson(String jsonPath) {

        final ObjectMapper objectMapper = new ObjectMapper();
        AlternativeResultBean results = new AlternativeResultBean();

        try {
            results = objectMapper.readValue(new File(jsonPath), new TypeReference<AlternativeResultBean>(){});
        } catch (JsonParseException e) {
            System.err.println("JsonParseException " + e.getMessage());
        } catch (JsonMappingException e) {
            System.err.println("JsonMappingException " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }

        return results;
    }

    public void persistTranscript(AlternativeResultBean results, String episodeId) {
        results.combineTranscriptsAndWords();
        
        Optional<Episode> episodeRS = episodeRepository.findById(episodeId);
        if (!episodeRS.isPresent()) {
            System.err.println("persisTranscript: episode id=" + episodeId + " not found");
            return;
        }
        
        Episode episode = episodeRS.get();
        Transcript transcript = Transcript.builder()
            .episode(episode)
            .transcript(results.getTranscripts())
            .wordTokens(results.buildWordTokenList())
            .build();
        episode.setTranscript(transcript);
        episodeRepository.save(episode);

        results.reset();
    }
}
