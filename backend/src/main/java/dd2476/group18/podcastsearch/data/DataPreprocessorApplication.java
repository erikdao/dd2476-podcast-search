package dd2476.group18.podcastsearch.data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataPreprocessorApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DataPreprocessorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // As a start, try to read the metadata.tsv file
        String projectDir = System.getProperty("user.dir");
        Path tsvPath = Paths.get(projectDir + "/data/podcasts-transcript/metadata.tsv");
        TsvMetadataBeanLoader loader = new TsvMetadataBeanLoader(tsvPath);
        List<MetadataBean> metadata = loader.getMetaDataBean(); 
    }
}