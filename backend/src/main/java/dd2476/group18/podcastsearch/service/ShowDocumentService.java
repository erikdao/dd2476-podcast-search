package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.ShowDocument;
import dd2476.group18.podcastsearch.repositories.ElasticShowRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowDocumentService {
    @Autowired
    private final ElasticShowRepository elasticShowRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * Manually create the `shows` index
     */
    public void createIndex() {
        elasticsearchRestTemplate.indexOps(ShowDocument.class).create();
    }

    public ShowDocument findById(String id) {
        return elasticShowRepository.findById(id).get();
    }

    public ShowDocument findByShowUri(String uri) {
        return elasticShowRepository.findByShowUri(uri);
    }

}
