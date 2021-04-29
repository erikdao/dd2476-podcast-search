package dd2476.group18.podcastsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        // final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        //         .connectedTo("192.168.192.91:9200")
        //         .build();

        // return RestClients.create(clientConfiguration).rest();
        RestClientBuilder builder = RestClient.builder(
        new HttpHost("192.168.192.91", 9200))
        .setHttpClientConfigCallback(new HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(
                    HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultIOReactorConfig(
                    IOReactorConfig.custom()
                        .setIoThreadCount(196)
                        .build());
            }
        });
        return new RestHighLevelClient(builder);
    }



}
