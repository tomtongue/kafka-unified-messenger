package com.tomtan.messenger.kafka;

import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("com.tomtan.messenger.kafka");
        String mode = System.getProperty("mode");
        String mapProps = System.getProperty("props");
        String topicsList = System.getProperty("topics");
        List<String> topics = Arrays.asList(topicsList.split(","));

        Properties conf = new Properties();
        switch(mode) {
            case("prod"):
                // TODO: producerSend
                break;

            case("cons"):
                Consumer consumer = new Consumer();
                consumer.setTopics(topics);
                // TODO: consumerReceive
                break;

            default:
                throw new IllegalArgumentException("Need to specify 'prod' or 'cons'");
        }


    }
}
