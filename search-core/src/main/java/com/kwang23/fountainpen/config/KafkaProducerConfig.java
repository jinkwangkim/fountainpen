package com.kwang23.fountainpen.config;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.kwang23.fountainpen.infra.enums.KafkaProducerEnum.KEY_WORLD_SEARCH_PRODUCER;

@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.bootstrapAddress}")
    private String kafkaBootstrap;

//    @Bean
//    public ProducerFactory<String, KeyWordSearchEvent> keyWordSearchEventProducerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrap);
//        return new DefaultKafkaProducerFactory<>(props,
//                KEY_WORLD_SEARCH_PRODUCER.getKeySerializer(), KEY_WORLD_SEARCH_PRODUCER.getValueSerializer());
//    }
    @Bean
    public KafkaTemplate<String, KeyWordSearchEvent> keyWordSearchEventKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrap);
        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(props,
                KEY_WORLD_SEARCH_PRODUCER.getKeySerializer(), KEY_WORLD_SEARCH_PRODUCER.getValueSerializer());

        return new KafkaTemplate<>(factory);
    }
}
