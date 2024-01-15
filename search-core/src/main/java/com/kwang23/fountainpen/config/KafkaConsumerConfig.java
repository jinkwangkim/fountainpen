package com.kwang23.fountainpen.config;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.kwang23.fountainpen.infra.enums.KafkaConsumerEnum.KEY_WORLD_SEARCH_CONSUMER;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${kafka.bootstrapAddress}")
    private String kafkaBootstrap;
    @Value("${kafka.groupId}")
    private String groupId;

//    @Bean
//    public ConsumerFactory<String, KeyWordSearchEvent> keyWordSearchEventConsumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrap);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        return new DefaultKafkaConsumerFactory<>(props, KEY_WORLD_SEARCH_CONSUMER.getKeyDeserializer(),
//                KEY_WORLD_SEARCH_CONSUMER.getValueDeserializer());
//    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KeyWordSearchEvent> keyWordSearchEventContainerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        DefaultKafkaConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory<>(props, KEY_WORLD_SEARCH_CONSUMER.getKeyDeserializer(),
                KEY_WORLD_SEARCH_CONSUMER.getValueDeserializer());

        ConcurrentKafkaListenerContainerFactory<String, KeyWordSearchEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
