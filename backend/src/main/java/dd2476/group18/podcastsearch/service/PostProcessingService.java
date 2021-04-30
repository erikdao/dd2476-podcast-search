package dd2476.group18.podcastsearch.service;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.models.PostProcessResult;
import dd2476.group18.podcastsearch.models.Clip;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.models.Transcript;
import dd2476.group18.podcastsearch.models.WordToken;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.Data;

@Data
@RequiredArgsConstructor
@RestController
public class PostProcessingService {
    final EpisodeRepository episodeRepository;
    private final EpisodeDocumentService episodeDocumentService;
    
    public List<PostProcessResult> postProcessElasticSearchResult(List<EpisodeDocument> relevantEpisodes, ArrayList<String> keywords){
        List<PostProcessResult> result = new ArrayList<PostProcessResult>();
        for (int i = 0; i < relevantEpisodes.size(); i++) {
            result.add(postProcesshandler(relevantEpisodes.get(i),keywords));
        }
        return result;
    }
    
    public PostProcessResult postProcesshandler(EpisodeDocument episodeObj, ArrayList<String> keywords) {
        PostProcessResult currentEpisodeRes = new PostProcessResult();
        currentEpisodeRes = addMetaData(currentEpisodeRes, episodeObj);
        List<Clip> clips = processClips(episodeObj, keywords);
        currentEpisodeRes.setClips(null);
        return currentEpisodeRes;    
    }

    public List<Clip> processClips(EpisodeDocument episode, ArrayList<String> keywords){
        Transcript currentTranscript = episodeRepository.findById(episode.getId()).get().getTranscript(); //gettoken or gettranscript
        List<WordToken> tokens = currentTranscript.getWordTokens();
        Collections.sort(tokens);
        
        List<Clip> clips = new ArrayList<Clip>();
        Clip newClip = new Clip();
        
        String tok;
        double startTime = -1;
        StringBuilder transcriptExcerpt = new StringBuilder("");
        Boolean isReading = false;
        for (int i = 0; i < tokens.size(); i++) {
            tok = tokens.get(i).getWord();
            if(isReading){
                transcriptExcerpt.append(tok);
            }
            if (keywords.contains(tok)) {
                if(startTime < 0){
                    newClip.startTime = tokens.get(i).getStartTime();
                    isReading = true;
                    transcriptExcerpt.append(tok);
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

    // This is a test made by Kalle and Caroline
    // @GetMapping("/search1/{query}")
    // public List<PostProcessResult> searchEpisodeByTranscript1(@PathVariable("query") String s) {
    //     List<EpisodeDocument> elasticRes = episodeDocumentService.searchEpisodeByTranscript(s);
    //     ArrayList<String> keywords = s.split(" ");
    //     List<PostProcessResult> result = postProcessElasticSearchResult(elasticRes,keywords);
    //     return result;
    // }
}








