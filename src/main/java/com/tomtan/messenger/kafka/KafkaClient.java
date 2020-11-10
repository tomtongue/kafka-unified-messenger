package com.tomtan.messenger.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface KafkaClient {
    void setClientId(String clientId);
    void setProps(Map<String, String> mapProps);
    void setTopics(List<String> topics);

    String getClientId();
    Map<String, String> getProps();
    List<String> getTopics();

}
