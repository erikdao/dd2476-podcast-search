package dd2476.group18.podcastsearch.dataloader;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeListBean {
    private List<AlternativeItemBean> alternatives;
}