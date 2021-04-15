package dd2476.group18.podcastsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableTransactionManagement
@RestController
public class PodcastSearchApplication {

	@RequestMapping("/")
	public String home() {
		return "Hello Podcast Search";
	}

	public static void main(String[] args) {
		SpringApplication.run(PodcastSearchApplication.class, args);
	}

}
