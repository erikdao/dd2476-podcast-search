package dd2476.group18.podcastsearch.models;

import java.io.Serializable;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class WordToken implements Serializable, Comparator<WordToken> {
    private String word;
    private double startTime;
    private double endTime;

    public double getStartTime(){
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    @Override
    public int compare(WordToken compareToken1, WordToken compareToken2) {
        // we are not sure on the order of subtraction
        return (int)(compareToken1.startTime - compareToken2.startTime);
    }

    @Override
    public String toString() {
        return "( " + word + ", " + startTime + ", " + endTime + " )";
    }

}
