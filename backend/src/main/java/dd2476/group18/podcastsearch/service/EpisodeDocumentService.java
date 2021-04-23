package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.EpisodeDocument;
import dd2476.group18.podcastsearch.repositories.EpisodeDocumentRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpisodeDocumentService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final EpisodeDocumentRepository episodeDocumentRepository;

    public EpisodeDocumentService(ElasticsearchOperations elasticsearchOperations, EpisodeDocumentRepository episodeDocumentRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.episodeDocumentRepository = episodeDocumentRepository;
    }

    // use elastic search to find a specific episode by id
    public EpisodeDocument findByEpisodeId(String id) {
        return episodeDocumentRepository.findByEpisodeId(id);
    }

    // use elastic search to find a specific episode by uri
    public EpisodeDocument findByEpisodeUri(String uri) {
        return episodeDocumentRepository.findByEpisodeUri(uri);
    }

    // use elastic search to find episodes that contains the given word in their transcripts
    public List<EpisodeDocument> searchEpisodeByTranscript(String s) {
        Criteria criteria = new Criteria("transcript").contains(s);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EpisodeDocument> searchHits = elasticsearchOperations.search(query, EpisodeDocument.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<EpisodeDocument> searchEpisodeByDescription(String... words) {
        if (words.length == 1) {
            System.out.println("One word query");
        } else {
            System.out.println("Multi-word query");
        }
        Criteria criteria = new Criteria("description").contains(words[0]).contains(words[1]);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EpisodeDocument> searchHits = elasticsearchOperations.search(query, EpisodeDocument.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
