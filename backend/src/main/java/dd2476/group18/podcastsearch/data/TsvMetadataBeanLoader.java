package dd2476.group18.podcastsearch.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.common.DataProcessingException;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.RowProcessorErrorHandler;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class TsvMetadataBeanLoader {
    private BufferedReader reader;
    private TsvParser parser;
    private TsvParserSettings parserSettings;
    private BeanListProcessor<MetadataBean> rowProcessor;

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
}
