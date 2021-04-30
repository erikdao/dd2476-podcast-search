package dd2476.group18.podcastsearch.config;

import java.util.concurrent.ForkJoinPool;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ThreadPoolConfiguration {
    @Bean
    ForkJoinPool workerPool() {
        return new ForkJoinPool();
    }
}
