package dd2476.group18.podcastsearch.service;

import dd2476.group18.podcastsearch.models.ShowDocument;
import dd2476.group18.podcastsearch.repositories.ShowDocumentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowDocumentService {
    @Autowired
    private final ShowDocumentRepository elasticShowRepository;

    public ShowDocument findById(String id) {
        return elasticShowRepository.findById(id).get();
    }

    public ShowDocument findByShowUri(String uri) {
        return elasticShowRepository.findByShowUri(uri);
    }
}
