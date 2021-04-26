package dd2476.group18.podcastsearch.models;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.Data;

/**
 * PostProcessResult
 */
@Data
@RequiredArgsConstructor
public class Clip implements Serializable{
    
    public double startTime;
    public double endTime;
    public String transcriptExcerpt;
    
}