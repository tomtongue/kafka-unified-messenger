package com.tomtan.messenger.kafka;

public interface KafkaClient {

    void setClientId(String clientId);

    String getClientId();
}
