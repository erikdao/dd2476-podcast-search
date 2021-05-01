package dd2476.group18.podcastsearch.importers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.repositories.EpisodeDocumentRepository;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.repositories.ShowDocumentRepository;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MetaDataLoader {
    private static final String METADATA_PATH = "/data/podcasts-transcript/metadata.tsv";

    private final String workingDir;
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;
    private final ShowDocumentRepository showDocumentRepository;
    private final EpisodeDocumentRepository episodeDocumentRepository;

    public void executePipeline(String ...args) {
        Instant start = Instant.now();

        Path tsvPath = Paths.get(workingDir + METADATA_PATH);
        TsvMetadataBeanLoader loader = new TsvMetadataBeanLoader(tsvPath);

        // Step 2. Create a set of shows and their corresponding epsiodes from the metadata beans
        List<MetadataBean> metadata = loader.getMetaDataBean();
        loader.createShowAndEpisodeList(metadata);
        Instant parsedEnd = Instant.now();
        Duration parsedTime = Duration.between(start, parsedEnd);
        log.info("Metadata parsed completed in " + parsedTime.toSeconds() + " seconds");

        // Step 3. Persist the shows and episodes to database
        ArrayList<Show> showList = loader.getShowList();
        // showRepository.saveAll(showList);

        showList.stream().parallel().forEach(show -> {
            showDocumentRepository.save(show.createShowDocument());
        });

        Instant showedEnd = Instant.now();
        Duration showedTime = Duration.between(parsedEnd, showedEnd);
        log.info("Show metadata imported and indexed in " + showedTime.toSeconds() + " seconds");

        ArrayList<Episode> episodeList = loader.getEpisodeList();
        // episodeRepository.saveAll(episodeList);

        episodeList.stream().parallel().forEach(episode -> {
            episodeDocumentRepository.save(episode.createEpisodeDocument(false));
        });

        Instant episodeEnd = Instant.now();
        Duration episodeTime = Duration.between(showedEnd, episodeEnd);
        log.info("Episode metadata imported and indexed in " + episodeTime.toSeconds() + " seconds");
    }
}
