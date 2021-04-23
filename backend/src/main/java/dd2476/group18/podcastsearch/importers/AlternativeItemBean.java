package dd2476.group18.podcastsearch.importers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeItemBean {
    private String transcript;
    private double confidence;
    private List<WordBean> words;
}
