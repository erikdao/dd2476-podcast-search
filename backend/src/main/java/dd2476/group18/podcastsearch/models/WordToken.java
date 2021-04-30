package dd2476.group18.podcastsearch.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import dd2476.group18.podcastsearch.views.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonView(View.List.class)
public class WordToken implements Serializable, Comparable<WordToken> {
    private String word;
    private boolean isHighlight;
    private double startTime;
    private double endTime;


    public int compareTo(WordToken compareToken) {
        // we are not sure on the order of subtraction
        return (int)(this.startTime - compareToken.startTime);
    }

}

