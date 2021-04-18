package dd2476.group18.podcastsearch.dataloader;

import dd2476.group18.podcastsearch.models.WordToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordBean {
    private String word;
    private String startTime;
    private String endTime;
    private int speakerTag;

    public WordToken createWordTokenObject() {
        // convert "2.900s" to 2.9
        double sTime = Double.parseDouble(startTime.replace("s", ""));
        double eTime = Double.parseDouble(endTime.replace("s", ""));

        WordToken token = WordToken.builder()
            .word(word)
            .startTime(sTime)
            .endTime(eTime)
            .build();
        
        return token;
    }
}
