package com.kwang23.fountainpen.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.search.HighlighterEncoder;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration{

//    public ElasticsearchClient elasticsearchClient2() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth("elastic","changeme");
//        List<String> authorization = headers.get("Authorization");
//        System.out.println("authorization : " + authorization);
//
//        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
//                .setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "ApiKey QkwxMVU0d0JlNHB4cWxjWk5Bbno6VHB0b0Yzc1JRbjJfMGFqSlNCNVc5UQ==")})
//                .build();
//
//        ElasticsearchTransport transport = new RestClientTransport(
//                restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
    @Override
    public RestHighLevelClient elasticsearchClient() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth("elastic","changeme");
//        List<String> authorization = headers.get("Authorization");
//        System.out.println("authorization : " + authorization);

//        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
//                .setDefaultHeaders(headers.get)
//                .build();

        // Create the transport with a Jackson mapper
//        ElasticsearchTransport transport = new RestClientTransport(
//                restClient, new JacksonJsonpMapper());
//
//        // And create the API client
//        return new ElasticsearchClient(transport);

        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo("host.docker.internal:9200")
                .withBasicAuth("elastic","changeme")
                .build();
        return RestClients.create(configuration).rest();
    }

}
