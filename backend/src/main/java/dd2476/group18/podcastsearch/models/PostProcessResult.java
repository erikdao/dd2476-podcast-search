package dd2476.group18.podcastsearch.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.models.Clip;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.repositories.TranscriptRepository;

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
    private List<Clip> clips;

    
    
}