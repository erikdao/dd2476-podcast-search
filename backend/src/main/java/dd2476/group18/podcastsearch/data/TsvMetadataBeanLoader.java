package dd2476.group18.podcastsearch.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.univocity.parsers.common.DataProcessingException;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.RowProcessorErrorHandler;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.Show;

/**
 * This class loads the metadata from TSV file and parse
 * them into MetadataBean objects
 */
public class TsvMetadataBeanLoader {
    // Loader stuff
    private BufferedReader reader;
    private TsvParser parser;
    private TsvParserSettings parserSettings;
    private BeanListProcessor<MetadataBean> rowProcessor;

    // Persistent stuff
    private HashSet<Show> shows = new HashSet<>();
    private HashSet<Episode> episodes = new HashSet<>();

    public TsvMetadataBeanLoader(Path tsvFile) {
        try {
            reader = Files.newBufferedReader(tsvFile);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + tsvFile.toString());
        }
        rowProcessor = new BeanListProcessor<MetadataBean>(MetadataBean.class);

        parserSettings = new TsvParserSettings();
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setMaxCharsPerColumn(4096 * 3);
        parserSettings.setProcessorErrorHandler(new RowProcessorErrorHandler(){
            @Override
            public void handleError(DataProcessingException error, Object[] inputRow, ParsingContext context) {
                System.err.println("Error processing row: " + Arrays.toString(inputRow));
                System.err.println("Error details: column '" + error.getColumnName() + "' (index " + error.getColumnIndex() + ") has value '" + inputRow[error.getColumnIndex()] + "'");
            }
        });

        parser = new TsvParser(parserSettings);
    }

    public List<MetadataBean> getMetaDataBean() {
        parser.parse(reader);
        return rowProcessor.getBeans();
    }

    public void createShowAndEpisodeList(List<MetadataBean> metaDataBean) {
        for (MetadataBean mb: metaDataBean) {
            // Create show instance
            Show show = mb.createShowInstance();
            shows.add(show);
            episodes.add(mb.createEpisodeInstance(show));
        }
    }

    public ArrayList<Show> getShowList() {
        return new ArrayList<Show>(shows);
    }

    public ArrayList<Episode> getEpisodeList() {
        return new ArrayList<Episode>(episodes);
    }
}
