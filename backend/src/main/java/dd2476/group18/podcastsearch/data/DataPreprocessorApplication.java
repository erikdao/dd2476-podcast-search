package dd2476.group18.podcastsearch.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.common.DataProcessingException;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.RowProcessorErrorHandler;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

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

        try {
            BufferedReader reader = Files.newBufferedReader(tsvPath);
            BeanListProcessor<MetadataBean> rowProcessor = new BeanListProcessor<MetadataBean>(MetadataBean.class);
            TsvParserSettings settings = new TsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            settings.setProcessor(rowProcessor);
            settings.setMaxCharsPerColumn(4096 * 3);
            settings.setProcessorErrorHandler(new RowProcessorErrorHandler(){
                @Override
                public void handleError(DataProcessingException error, Object[] inputRow, ParsingContext context) {
                    System.err.println("Error processing row: " + Arrays.toString(inputRow));
                    System.err.println("Error details: column '" + error.getColumnName() + "' (index " + error.getColumnIndex() + ") has value '" + inputRow[error.getColumnIndex()] + "'");
                }
            });

            TsvParser parser = new TsvParser(settings);
            parser.parse(reader);
            List<MetadataBean> metadata = rowProcessor.getBeans();
            System.out.println("Sample: " + metadata.get(0).getLanguage());
        } catch (IOException e) {
            System.err.println("Cannot read file " + tsvPath.toString());
        }
    }
}