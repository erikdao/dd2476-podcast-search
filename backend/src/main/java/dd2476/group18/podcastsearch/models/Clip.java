package dd2476.group18.podcastsearch.models;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.Data;

/**
 * PostProcessResult
 */
@Data
@RequiredArgsConstructor
public class Clip implements Serializable, Comparable{
    
    public double startTime;
    public double endTime;
    public String transcriptExcerpt;
    public int numKeywords;

    public int compareTo(Clip clip) {
        // we are not sure on the order of subtraction
        return (int)(this.numKeywords - clip.numKeywords);
    }
    
}