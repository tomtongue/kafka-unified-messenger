package com.tomtan.messenger.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.*;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class MessageProducer implements KafkaClient {
    private String clientId;
    private Map<String, String> mapProps; // TODO: Implementation of the parser
    private String topic;
    KafkaProducer<Integer, String> producer;

    public MessageProducer(Properties props) { this.producer = new KafkaProducer<>(props); }

    // Setter
    public void setClientId(String clientId) { // TODO: replace with abstractClass
        if(StringUtils.isEmpty(clientId)) {
            this.clientId = "kafka-producer-" + UUID.randomUUID().toString();
        } else {
            this.clientId = clientId;
        }
    }

    public void setProps(Map<String, String> mapProps) {
        if(mapProps.isEmpty()) { throw new IllegalArgumentException("Specify broker servers, etc"); }
        // TODO: Setting configuration by getting parameter
    }

    public void setTopic(String topic) {
        if(StringUtils.isEmpty(topic)) { throw new IllegalArgumentException("Topic is required."); }
        this.topic = topic;
    }

    // Getter
    public String getClientId() { return this.clientId; }
    public String getTopic() { return this.topic; }
    public Map<String, String> getProps() { return this.mapProps; }

    // Wrapped methods
    public void close() { this.producer.close(); }



    // publish
    public void publish(String topic, int key, String value) {
        ProducerRecord<Integer, String> record = new ProducerRecord<>(topic, key, value);

        this.producer.send(record, (recordMetadata, e) -> { // TODO: Output via logger
            if(StringUtils.isEmpty(recordMetadata.toString())) {
                System.out.printf(
                        "published successfully: Partition: %s, Offset: %s",
                        recordMetadata.partition(), recordMetadata.offset());
            } else {
                System.out.printf("failed to publish data: %s", e.getMessage());
            }
        });
    }
}
