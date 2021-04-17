package dd2476.group18.podcastsearch.data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dd2476.group18.podcastsearch.models.Show;
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

    @Autowired
    public DataPreprocessorApplication(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public void run(String... args) {
        importMetaData(args);
    }

    public void importMetaData(String... args) {
        // Step 1. Read and parse the metadata tsv file
        String currentDir = System.getProperty("user.dir");
        Path projectDir = Paths.get(currentDir);
        Path tsvPath = Paths.get(projectDir + "/data/podcasts-transcript/metadata.tsv");
        TsvMetadataBeanLoader loader = new TsvMetadataBeanLoader(tsvPath);

        // Step 2. Create a set of shows and their corresponding epsiodes from the metadata beans
        List<MetadataBean> metadata = loader.getMetaDataBean();
        HashSet<Show> shows = loader.createShowSet(metadata);

        // Step 3. Persist the shows and episodes to database
        ArrayList<Show> showList = new ArrayList<Show>(shows);
        showRepository.saveAll(showList);
        
        System.out.println("Inserted " + shows.size() + " shows to database!");
    }
}