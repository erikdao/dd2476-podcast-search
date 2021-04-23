package dd2476.group18.podcastsearch.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataPreprocessorApplication implements CommandLineRunner {
    // public static void main(String[] args) {
    //     System.out.println("Starting the application");
    //     SpringApplication.run(DataPreprocessorApplication.class, args);
    //     System.out.println("Application finished");
    // }

    @Override
    public void run(String... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }
}