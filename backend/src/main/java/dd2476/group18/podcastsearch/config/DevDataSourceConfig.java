package dd2476.group18.podcastsearch.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("dev")
public class DevDataSourceConfig implements DataSourceConfig {
    public void setup() {
        log.info("Setting up data source for Dev environment");
    }
}
