package com.kwang23.fountainpen.infra.enums;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
public enum KafkaProducerEnum {
    KEY_WORLD_SEARCH_PRODUCER("keyword-search-event");

    private String topic;
    private Serializer keySerializer;
    private Serializer valueSerializer;

    KafkaProducerEnum(String topic) {
        this.topic = topic;
        this.keySerializer = new StringSerializer();
        this.valueSerializer = new JsonSerializer();
    }

    public String getTopic() {
        return this.topic;
    }
    public Serializer getKeySerializer() {
        return this.keySerializer;
    }
    public Serializer getValueSerializer() {
        return this.valueSerializer;
    }
}
