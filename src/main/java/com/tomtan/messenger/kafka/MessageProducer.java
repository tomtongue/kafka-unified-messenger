package com.tomtan.messenger.kafka;


import com.google.common.base.Strings;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;
import java.util.Properties;


public class MessageProducer extends KafkaAbsClient {
    Properties props;
    Map<String, String> mapProps;
    KafkaProducer<Integer, String> producer;

    private String clientId;
    private String topic;


    public MessageProducer(Map<String, String> mapProps) {
        if(mapProps.isEmpty()) throw new IllegalArgumentException("The producer configuration is empty. You need to specify them.");

        this.props = new Properties();
        this.mapProps = mapProps;

        for(String key: mapProps.keySet()) {
            this.props.setProperty(key, mapProps.get(key));
        }
        this.producer = new KafkaProducer<>(this.props);
    }


    public void setTopic(String topic) {
        if(Strings.isNullOrEmpty(topic)) { throw new IllegalArgumentException("Topic is required."); }
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
            if(Strings.isNullOrEmpty(recordMetadata.toString())) {
                System.out.printf(
                        "published successfully: Partition: %s, Offset: %s",
                        recordMetadata.partition(), recordMetadata.offset());
            } else {
                System.out.printf("failed to publish data: %s", e.getMessage());
            }
        });
    }
}
