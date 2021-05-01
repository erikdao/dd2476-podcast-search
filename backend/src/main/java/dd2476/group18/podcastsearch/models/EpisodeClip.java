package dd2476.group18.podcastsearch.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import dd2476.group18.podcastsearch.views.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class representing a clip from the transcript of an episode.
 * This class is not persisted to the database or the index.
 * Each clip consists of a number of tokens, has a start time and end time in second
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.Minimal.class)
public class EpisodeClip {
    private Integer order;
    private Double startTime;
    private Double endTime;
    private List<WordToken> wordTokens;
}
