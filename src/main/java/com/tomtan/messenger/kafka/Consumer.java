package com.tomtan.messenger.kafka;

import java.util.List;
import java.util.Map;

public class Consumer implements KafkaClient {
    private Map<String, String> mapProps;
    private List<String> topics;

    public void setProps(Map<String, String> mapProps) {
        if(mapProps.isEmpty()) { throw new IllegalArgumentException("Specify broker server, etc"); }
        // Implement
    }
    public void setTopics(List<String> topics) {
        if(topics.isEmpty()) { throw new IllegalArgumentException("Specify one or more topics"); }
        this.topics = topics;
    }
    public List<String> getTopics() { return this.topics; }
    public Map<String, String> getProps() { return this.mapProps; }

}
