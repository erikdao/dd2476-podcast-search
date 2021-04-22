package dd2476.group18.podcastsearch.dataloader;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.repositories.ShowRepository;

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
    public DataPreprocessorApplication(ShowRepository showRepository, EpisodeRepository episodeRepository) {
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
    }

    @Override
    public void run(String... args) {
        importMetaData(args);
        // importTranscriptData(args);
    }

    public void importMetaData(String... args) {
        String currentDir = System.getProperty("user.dir");
        Path projectDir = Paths.get(currentDir).getParent();
        MetaDataLoader loader = new MetaDataLoader(projectDir.toString(), this.showRepository, this.episodeRepository);
        loader.executePipeline(args);
    }

    public void importTranscriptData(String... args) {
        String workingDir = System.getProperty("user.dir");
        TranscriptLoader loader = new TranscriptLoader(workingDir, this.episodeRepository);
        loader.executePipeline(args);
    }
}
