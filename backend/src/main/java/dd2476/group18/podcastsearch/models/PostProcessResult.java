package dd2476.group18.podcastsearch.models;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import lombok.Data;

/**
 * PostProcessResult
 */
@Data
@RequiredArgsConstructor
public class PostProcessResult implements Serializable{
    private String id;
    private String name;
    private ArrayList<Clip> clips;
}
