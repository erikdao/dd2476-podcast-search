package dd2476.group18.podcastsearch.controllers;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import javax.websocket.server.PathParam;

import org.elasticsearch.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.PostProcessResult;
import dd2476.group18.podcastsearch.models.Clip;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.models.Transcript;
import dd2476.group18.podcastsearch.models.WordToken;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import dd2476.group18.podcastsearch.service.EpisodeDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import org.springframework.web.bind.annotation.*;

@Data
@RequiredArgsConstructor
@RestController
public class PostProcessingService {
    final EpisodeRepository episodeRepository;
    private final EpisodeDocumentService episodeDocumentService;
    
    public List<PostProcessResult> postProcessElasticSearchResult(List<EpisodeDocument> relevantEpisodes, String keywords){
        List<PostProcessResult> result = new ArrayList<PostProcessResult>();
        for (int i = 0; i < relevantEpisodes.size(); i++) {
            result.add(postProcesshandler(relevantEpisodes.get(i),keywords));
        }
        return result;
    }
    
    public PostProcessResult postProcesshandler(EpisodeDocument episodeObj, String keywords) {
        PostProcessResult currentEpisodeRes = new PostProcessResult();
        currentEpisodeRes = addMetaData(currentEpisodeRes, episodeObj);
        List<Clip> clips = processClip(episodeObj, keywords);
        Collections.sort(clips);
        int size = clips.size();
        if (size > 4) {
            currentEpisodeRes.setClips(clips.subList(0, 4));
        } else {
            currentEpisodeRes.setClips(clips);
        }
        for (int i = 0; i < clips.size(); i++) {
            System.out.println("Clip: "+ 1);
            System.out.println(clips.get(i).numKeywords);
        }
        return currentEpisodeRes;
    } 

    public List<Clip> processClip(EpisodeDocument episode, String s){
        ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(s.split(" ")));
        Transcript currentTranscript = episodeRepository.findById(episode.getId()).get().getTranscript(); //gettoken or gettranscript
        List<WordToken> tokens = currentTranscript.getWordTokens();
        // it seems like it already is sorted
        // Collections.sort(tokens);

        List<Clip> clips = new ArrayList<Clip>();
        String tok;
        StringBuilder transcriptExc = new StringBuilder("");
        double start = 0;
        double end = 0;
        double limit = 40;
        int numKeywords = 0;
        
        boolean isbuildingTranscript = false;
        // this handles multiword queries
        for (int i = 0; i < tokens.size(); i++) {
            tok = tokens.get(i).getWord();

            if(isbuildingTranscript && tokens.get(i).getStartTime()-end > limit){
                //create and add clip
                Clip newClip = new Clip();
                newClip.endTime = tokens.get(i).getEndTime();
                newClip.startTime = start;
                newClip.transcriptExcerpt = transcriptExc.toString();
                newClip.numKeywords = numKeywords;
                clips.add(newClip);
                transcriptExc.setLength(0);
                isbuildingTranscript = false; 
                numKeywords = 0;               
            } else if (isbuildingTranscript) {
                transcriptExc.append(tok + " ");
            } 
            if (keywords.contains(tok)) {
                if(isbuildingTranscript){
                    end = tokens.get(i).getEndTime();
                } else {
                    isbuildingTranscript = true;
                    start = tokens.get(i).getStartTime();
                    end = tokens.get(i).getEndTime();   
                    transcriptExc.append(tok + " ");
                }
                numKeywords ++;
            }
        }
        return clips;
    }

    public List<Clip> processClips_OLD(EpisodeDocument episode, String s){
        ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(s.split(" ")));
        Transcript currentTranscript = episodeRepository.findById(episode.getId()).get().getTranscript(); //gettoken or gettranscript
        List<WordToken> tokens = currentTranscript.getWordTokens();
        // Collections.sort(tokens);
        
        List<Clip> clips = new ArrayList<Clip>();
        Clip newClip = new Clip();
        
        String tok;
        double startTime = -1;
        StringBuilder transcriptExcerpt = new StringBuilder("");
        Boolean isReading = false;
        for (int i = 0; i < tokens.size(); i++) {
            tok = tokens.get(i).getWord();
            if(isReading){
                transcriptExcerpt.append(tok+ " ");
            }
            if (keywords.contains(tok)) {
                if(startTime < 0){
                    newClip.startTime = tokens.get(i).getStartTime();
                    isReading = true;
                    transcriptExcerpt.append(tok+" ");
                }
                newClip.endTime = tokens.get(i).getEndTime();
            }
        }
        newClip.setTranscriptExcerpt(transcriptExcerpt.toString());
        clips.add(newClip);
        return clips;
    }

    public PostProcessResult addMetaData(PostProcessResult currentEpisodeRes, EpisodeDocument episode) {
        currentEpisodeRes.setName(episode.getEpisodeName());
        currentEpisodeRes.setId(episode.getId());
        return currentEpisodeRes;
    }

    // Endpoint for post-processing
    @GetMapping("/search1/{query}")
    public List<PostProcessResult> searchEpisodeByTranscript1(@PathVariable("query") String s) {
        System.out.println("Endpoint /search1/" +s+ " running");
        List<EpisodeDocument> elasticRes = episodeDocumentService.searchWithMatchQuery("transcript", s, 0, 10, "match");
        List<PostProcessResult> result = postProcessElasticSearchResult(elasticRes, s);
        return result;
    }
}






