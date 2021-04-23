package dd2476.group18.podcastsearch.importers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dd2476.group18.podcastsearch.models.WordToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeResultBean {
    private List<AlternativeListBean> results;

    @JsonIgnore
    private String transcripts;
    @JsonIgnore
    private List<WordBean> wordBeans = new ArrayList<>();
    @JsonIgnore
    private String episodeId;

    public void combineTranscriptsAndWords() {
        ArrayList<String> transcriptList = new ArrayList<>();
        for (AlternativeListBean result: results) {
            for (AlternativeItemBean alternative: result.getAlternatives()) {
                try {
                    String transcript = alternative.getTranscript();
                    if (transcript != null) {
                        transcriptList.add(transcript);
                    } else {
                        // There is no transcript, it's the alternative that contains
                        // only the `words` key
                        try {
                            wordBeans = alternative.getWords();
                        } catch (NullPointerException e) {
                            log.error("Cannot that word tokens attribute, anomaly in data");
                        }
                    }
                } catch (NullPointerException e) {
                    log.error("NullPointerException while trying to get transcript attribute from AlternativeItemBean. " + e.getMessage());
                }
            }
        }

        // Join the transcript excerpts to get a single transcript
        transcripts = String.join("", transcriptList);
    }

    public ArrayList<WordToken> buildWordTokenList() {
        ArrayList<WordToken> tokens = new ArrayList<>();
        for (WordBean wordBean: wordBeans) {
            tokens.add(wordBean.createWordTokenObject());
        }
        return tokens;
    }

    public void reset() {
        transcripts = null;
        wordBeans = new ArrayList<>();
    }
}
