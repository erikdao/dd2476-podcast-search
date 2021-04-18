package dd2476.group18.podcastsearch.dataloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.models.Transcript;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import dd2476.group18.podcastsearch.repositories.TranscriptRepository;

/**
 * A util to import show and episode metadata
 * By default, this application should be run by the gradle tool
 * from the `/backend` directory
 */
@Component
public class DataPreprocessorApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataPreprocessorApplication.class);
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public DataPreprocessorApplication(ShowRepository showRepository, EpisodeRepository episodeRepository)
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
    }

    @Override
    public void run(String... args) {
        // importMetaData(args);
        importTranscriptData(args);
    }

    public void importMetaData(String... args) {
        // Step 1. Read and parse the metadata tsv file
        String currentDir = System.getProperty("user.dir");
        Path projectDir = Paths.get(currentDir);
        Path tsvPath = Paths.get(projectDir + "/data/podcasts-transcript/metadata.tsv");
        TsvMetadataBeanLoader loader = new TsvMetadataBeanLoader(tsvPath);

        // Step 2. Create a set of shows and their corresponding epsiodes from the metadata beans
        List<MetadataBean> metadata = loader.getMetaDataBean();
        loader.createShowAndEpisodeList(metadata);

        // Step 3. Persist the shows and episodes to database
        ArrayList<Show> showList = loader.getShowList();
        showRepository.saveAll(showList); 
        System.out.println("Inserted " + showList.size() + " shows to database!");

        ArrayList<Episode> episodeList = loader.getEpisodeList();
        episodeRepository.saveAll(episodeList); 
        System.out.println("Inserted " + episodeList.size() + " episodes to database!");
    }

    public void importTranscriptData(String... args) {
        String sampleEpisodeId = "1RuLO0g4ps1p6kT3NB194o";

        String currentDir = System.getProperty("user.dir");
        Path projectDir = Paths.get(currentDir);
        Path tsvPath = Paths.get(projectDir + "/data/podcasts-transcript/spotify-podcasts-2020/podcasts-transcripts/" +
                                    "0/E/show_0E2L8zPYhApYkmWWFef7aK/1RuLO0g4ps1p6kT3NB194o.json");

        final ObjectMapper objectMapper = new ObjectMapper();
        AlternativeResultBean alListBean = new AlternativeResultBean();

        try {
            alListBean = objectMapper.readValue(
                new File(tsvPath.toString()),
            new TypeReference<AlternativeResultBean>(){}
            );
        } catch (JsonParseException e) {
            System.err.println("JsonParseException " + e.getMessage());
        } catch (JsonMappingException e) {
            System.err.println("JsonMappingException " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }

        alListBean.combineTranscriptsAndWords();

        Episode episode = episodeRepository.findById(sampleEpisodeId).orElseThrow();
        Transcript transcript = Transcript.builder()
            .episode(episode)
            .transcript(alListBean.getTranscripts())
            .wordTokens(alListBean.buildWordTokenList())
            .build();
        episode.setTranscript(transcript);
        episodeRepository.save(episode);
    }
}