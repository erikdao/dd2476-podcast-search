package dd2476.group18.podcastsearch.models;

import java.io.Serializable;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordToken implements Serializable, Comparable<WordToken> {
    private String word;
    private double startTime;
    private double endTime;


    public int compareTo(WordToken compareToken) {
        // we are not sure on the order of subtraction
        return (int)(this.startTime - compareToken.startTime);
    }

}

