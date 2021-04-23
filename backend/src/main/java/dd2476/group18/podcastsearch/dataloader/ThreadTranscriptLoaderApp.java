package dd2476.group18.podcastsearch.dataloader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = {
    WebMvcAutoConfiguration.class
})
@RequiredArgsConstructor
@Slf4j
public class ThreadTranscriptLoaderApp implements CommandLineRunner {
    final ForkJoinPool workerPool;

    @Override
    public void run(String... args) throws Exception {
        Map<String, Long> counters = new ConcurrentHashMap<>();

        Thread summary = new Thread(() -> {
            while (!workerPool.isShutdown()) {
                try {
                    log.info("Visited: {}", counters);
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        workerPool.submit(() -> {
            
        });

        summary.start();
        
    }
    
}
