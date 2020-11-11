package com.tomtan.messenger.kafka;

import java.time.Duration;
import java.util.*;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.commons.lang3.StringUtils;


public class MessageConsumer implements KafkaClient {
    private String clientId;
    private String groupId;
    private Map<String, String> mapProps; // TODO: Implementation of the parser
    private List<String> topics;
    KafkaConsumer<Integer, String> consumer;

    public MessageConsumer(Properties props) {
        this.consumer = new KafkaConsumer<>(props);
    }

    // Setter
    public void setClientId(String clientId) {
        if(StringUtils.isEmpty(clientId)) {
            this.clientId = "kafka-consumer-" + UUID.randomUUID().toString();
        } else {
            this.clientId = clientId;
        }
    }

    public void setGroupId(String groupId) {
        if(StringUtils.isEmpty(groupId)) { throw new IllegalArgumentException("Specify groupId, etc"); }

        this.groupId = groupId;
    }

    public void setProps(Map<String, String> mapProps) {
        if(mapProps.isEmpty()) { throw new IllegalArgumentException("Specify broker servers, etc"); }
        // TODO: Setting configuration by getting parameter

    }
    public void setTopics(List<String> topics) {
        if(topics.isEmpty()) { throw new IllegalArgumentException("Specify one or more topics"); }
        this.topics = topics;
    }

    // Getter
    public String getClientId() { return this.clientId; }
    public String getGroupId() { return this.groupId; }
    public List<String> getTopics() { return this.topics; }
    public Map<String, String> getProps() { return this.mapProps; }

    // Wrapped methods
    public void subscribe(List<String> topicsList) {
        this.consumer.subscribe(topicsList);
    }
    public ConsumerRecords<Integer, String> poll(Duration timeout) { return this.consumer.poll(timeout); }

    // Show subscribed messages
    public String returnMessage(ConsumerRecord<Integer, String> record) {
        return String.format("Received (%s, %s)%n", record.key(), record.value());
    }

    // Commit offset
    public void offsetCommit(ConsumerRecord<Integer, String> record, Consumer<Integer, String> consumer) {
        TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
        OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(record.offset() + 1);
        Map<TopicPartition, OffsetAndMetadata> commit = Collections.singletonMap(topicPartition, offsetAndMetadata);
        consumer.commitSync(commit);
    }
}
