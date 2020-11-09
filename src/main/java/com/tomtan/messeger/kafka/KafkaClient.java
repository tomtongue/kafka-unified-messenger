package com.tomtan.messeger.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface KafkaClient {
    void setProps(Map<String, String> mapProps);
    void setTopics(ArrayList<String> topics);
}
