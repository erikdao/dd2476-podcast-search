package dd2476.group18.podcastsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import dd2476.group18.podcastsearch.controllers.PostProcessController;


@SpringBootApplication
@RestController
public class PodcastSearchApplication {
	public static void main(String[] args) {
		PostProcessController p;
        p.postProcessTranscript(null);
        System.out.println("can we run main and print??");
		SpringApplication.run(PodcastSearchApplication.class, args);
	}

}
