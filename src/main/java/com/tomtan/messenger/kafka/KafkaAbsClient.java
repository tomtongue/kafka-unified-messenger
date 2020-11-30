package com.tomtan.messenger.kafka;

import com.google.common.base.Strings;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public abstract class KafkaAbsClient implements KafkaClient {
    public Properties props;
    public Map<String, String> mapProps;
    public String clientId;

    public void setClientId(String clientId) { // TODO: replace with abstractClass
        if(Strings.isNullOrEmpty(clientId)) {
            this.clientId = "kafka-client-" + UUID.randomUUID().toString();
        } else {
            this.clientId = clientId;
        }
    }
}
