package dd2476.group18.podcastsearch.controllers;

import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.Clip;
import javax.websocket.server.PathParam;

import org.elasticsearch.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.Episode;
import dd2476.group18.podcastsearch.models.WordToken;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostProcessController {
    final EpisodeRepository episodeRepository;
    
    class Clip {
        double startTime;
        double endTime;
        public Clip (double start, double end){
            startTime = start;
            endTime = end;
        }
    }
    
    
    public ArrayList<Clip> postProcessTranscript(ArrayList<WordToken> tokens) {
        ArrayList<WordToken> mockData = new ArrayList<WordToken>();
        WordToken tok1 = new WordToken("test", 3.0, 3.5);
        WordToken tok2 = new WordToken("name", 2.1, 2.9);
        WordToken tok3 = new WordToken("hej", 1.2, 1.8);

        mockData.add(tok1);
        mockData.add(tok2);
        mockData.add(tok3);

        // sort tokens by time
        Collections.sort(mockData, WordToken.comparator);
        
        // build clip
        ArrayList<Clip> result = new ArrayList<Clip>();
        Clip clip = new Clip(mockData.get(0).getStartTime(), mockData.get(mockData.size()-1).getEndTime());
        result.add(clip); 
        for (int i = 0; i < mockData.size(); i++) {
            System.out.println( mockData.get(i).getStartTime());
        }  
        return result;
    }
    

    // public static void main(String args[]) {
    //     PostProcessController p = new PostProcessController();
    //     p.postProcessTranscript(null);
    //     System.out.println("can we run main and print??");
    // }

}
