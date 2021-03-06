package dd2476.group18.podcastsearch.importers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import dd2476.group18.podcastsearch.repositories.EpisodeDocumentRepository;
import dd2476.group18.podcastsearch.repositories.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class TranscriptImporterCommand implements CommandLineRunner {
    final ForkJoinPool workerPool;
    @Autowired
    final EpisodeRepository episodeRepository;
    final EpisodeDocumentRepository episodeDocumentRepository;

    @Override
    public void run(String... args) throws Exception {
        // log.info("Importing Episodes' transcripts...");
        // importTranscript(args);
    }

    private void importTranscript(String... args) {
        Path projectDir = Paths.get(System.getProperty("user.dir")).getParent();
        Path dataDir = Paths.get(projectDir.toString(), "data", "podcasts-transcript", "spotify-podcasts-2020", "podcasts-transcripts");
        
        Map<String, Long> counters = new ConcurrentHashMap<>();

        Thread summary = new Thread(() -> {
            Instant start = Instant.now();
            while (!workerPool.isShutdown()) {
                try {
                    Long sum = 0L;
                    for (Map.Entry<String, Long> counter: counters.entrySet()) {
                        sum += counter.getValue();
                    }
                    Instant intermediate = Instant.now();
                    Duration elapsed = Duration.between(start, intermediate);
                    log.info("Complete " + sum + " episodes, time elapsed: " + elapsed.toMinutes() + " mins");
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            log.info("Total time: " + timeElapsed.toMinutes() + " minutes");
        });

        workerPool.submit(() -> {
            try {
                Files.walk(dataDir)
                    .parallel()
                    .onClose(workerPool::shutdown)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".json"))
                    .forEach(p -> {
                        String fileName = p.getFileName().toString();
                        String episodeId = fileName.replace(".json", "");
                        TranscriptLoader loader = new TranscriptLoader(episodeRepository, episodeDocumentRepository);
                        AlternativeResultBean transcriptBean = loader.loadTranscriptFromJson(p.toString());
                        loader.persistTranscript(transcriptBean, episodeId);

                        String key = Thread.currentThread().getName();
                        counters.put(key, counters.getOrDefault(key, 0L) + 1);
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        summary.start();
    }
}
