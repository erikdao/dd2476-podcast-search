package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.ShowDocument;
import dd2476.group18.podcastsearch.repositories.ShowDocumentRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class ShowDocumentService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ShowDocumentRepository showDocumentRepository;

    public ShowDocumentService(ElasticsearchOperations elasticsearchOperations, ShowDocumentRepository showDocumentRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.showDocumentRepository = showDocumentRepository;
    }

    public ShowDocument findByShowId(String id) {
        return showDocumentRepository.findByShowId(id);
    }

    public ShowDocument findByShowUri(String uri) {
        return showDocumentRepository.findByShowUri(uri);
    }

}
