package dd2476.group18.podcastsearch.dataloader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.Show;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.repositories.ShowRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MetaDataLoader {
    private static final String METADATA_PATH = "/data/podcasts-transcript/metadata.tsv";

    private String workingDir;
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;

    public void executePipeline(String ...args) {
        Path tsvPath = Paths.get(workingDir + METADATA_PATH);
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
}
