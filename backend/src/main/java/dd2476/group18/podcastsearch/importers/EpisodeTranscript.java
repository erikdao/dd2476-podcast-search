package dd2476.group18.podcastsearch.importers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeTranscript {
    private String transcript;
    private List<WordBean> words;
    
}
