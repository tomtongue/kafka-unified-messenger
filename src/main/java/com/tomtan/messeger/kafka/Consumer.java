package com.tomtan.messeger.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Consumer implements KafkaClient {
    private List<String> topics;
    public void setProps(Map<String, String> mapProps) {
        if(mapProps.isEmpty()) { throw new IllegalArgumentException("Specify broker server, etc"); }
        // Implement
    }
    public void setTopics(ArrayList<String> topics) {
        if(topics.isEmpty()) { throw new IllegalArgumentException("Specify one or more topics"); }
        for(String topic: topics) { topics.add(topic); }
    }



}
