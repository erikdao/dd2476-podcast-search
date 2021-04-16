package dd2476.group18.podcastsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class PodcastSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(PodcastSearchApplication.class, args);
	}

}
