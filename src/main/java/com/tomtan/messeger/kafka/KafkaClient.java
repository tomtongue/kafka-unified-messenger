package com.tomtan.messeger.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface KafkaClient {
    void setProps(Map<String, String> mapProps);
    void setTopics(List<String> topics);

    Map<String, String> getProps();
    List<String> getTopics();

}
