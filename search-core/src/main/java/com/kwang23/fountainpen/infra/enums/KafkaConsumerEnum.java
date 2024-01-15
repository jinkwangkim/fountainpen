package com.kwang23.fountainpen.infra.enums;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
public enum KafkaConsumerEnum {
    KEY_WORLD_SEARCH_CONSUMER("keyword-search-event");
    private String topic;
    private Deserializer keyDeserializer;
    private Deserializer valueDeserializer;

    KafkaConsumerEnum(String topic) {
        this.topic = topic;
        this.keyDeserializer = new StringDeserializer();
        this.valueDeserializer = new JsonDeserializer<>(KeyWordSearchEvent.class);
    }

    public String getTopic() {
        return this.topic;
    }
    public Deserializer getKeyDeserializer() {
        return this.keyDeserializer;
    }
    public Deserializer getValueDeserializer() {
        return this.valueDeserializer;
    }
}
