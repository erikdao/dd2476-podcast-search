package dd2476.group18.podcastsearch.dataloader;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * A util to import show and episode metadata
 * By default, this application should be run by the gradle tool
 * from the `/backend` directory
 */
@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class MetaDataImporterCommand implements CommandLineRunner {
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;

    @Override
    public void run(String... args) {
        // importMetaData(args); 
    }

    private void importMetaData(String... args) {
        log.info("Start importing metadata");
        String currentDir = System.getProperty("user.dir");
        Path projectDir = Paths.get(currentDir).getParent();
        MetaDataLoader loader = new MetaDataLoader(projectDir.toString(), this.showRepository, this.episodeRepository);
        loader.executePipeline(args);
    }
}
