package com.tomtan.messenger.kafka;

import com.google.common.base.Strings;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MessageConsumer extends KafkaAbsClient {
    KafkaConsumer<Integer, String> consumer;

    private String groupId;
    private List<String> topics;


    public MessageConsumer(Map<String, String> mapProps) {
        if(mapProps.isEmpty()) throw new IllegalArgumentException("The consumer configuration is empty. You need to specify them.");

        this.props = new Properties();
        this.mapProps = mapProps;

        for(String key: mapProps.keySet()) {
            this.props.setProperty(key, mapProps.get(key));
        }
        this.consumer = new KafkaConsumer<>(this.props);
    }


    public void setGroupId(String groupId) {
        if(Strings.isNullOrEmpty(groupId)) { throw new IllegalArgumentException("Specify groupId, etc"); }

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
        return String.format("Received (%s, %s)", record.key(), record.value());
    }

    // Commit offset
    public void offsetCommit(ConsumerRecord<Integer, String> record, Consumer<Integer, String> consumer) {
        TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
        OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(record.offset() + 1);
        Map<TopicPartition, OffsetAndMetadata> commit = Collections.singletonMap(topicPartition, offsetAndMetadata);
        consumer.commitSync(commit);
    }
}
