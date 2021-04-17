package dd2476.group18.podcastsearch.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordToken implements Serializable {
    private String word;
    private double startTime;
    private double endTime;
}
