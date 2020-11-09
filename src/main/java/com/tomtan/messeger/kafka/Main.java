package com.tomtan.messeger.kafka;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String mapProps = System.getProperty("props");
        String topicsList = System.getProperty("topics");
        List<String> topics = Arrays.asList(topicsList.split(","));

        Properties conf = new Properties();
        Consumer consumer = new Consumer();
        consumer.setTopics(topics);

    }
}
