package dd2476.group18.podcastsearch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration for application
 */
@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer(){
            @Autowired
            private Environment env;
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String url = env.getProperty("cors.url");
                registry.addMapping("/api/**")
                    .allowedOrigins(url)
                    .allowedMethods("GET", "POST", "OPTIONS");
            }
        };
    }
}