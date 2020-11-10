package com.tomtan.messenger.kafka;

import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        // Currently, just using `mode` and `topics` parameters
        Logger logger = Logger.getLogger("com.tomtan.messenger.kafka");
        String mode = System.getProperty("mode");
        String bootstrapServers = System.getProperty("servers"); // TODO: Move to mapProps param

        String clientId = System.getProperty("id");
        String groupId = System.getProperty("gid");
        String mapProps = System.getProperty("props"); // TODO: Implementation
        String topics = System.getProperty("topics");
        List<String> topicsList = Arrays.asList(topics.split(","));

        // Move this part to each components in the future
        Properties conf = new Properties();
        conf.setProperty("bootstrap.servers", bootstrapServers);
        conf.setProperty("group.id", groupId);
        conf.setProperty("enable.auto.commit", "false");
        conf.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        conf.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        String inputLog = String.format(
                "Mode: %s, specified servers: %s, groupId: %s, topics: %s",
                mode, bootstrapServers, groupId, topicsList.toString());
        logger.info(inputLog);

        switch(mode) {
            case("prod"):
                // TODO: Implementation of Producer
                break;

            case("cons"):
                MessageConsumer messageConsumer = new MessageConsumer(conf);
                messageConsumer.setGroupId(groupId);
                messageConsumer.setTopics(topicsList);
                // messageConsumer.consumer.subscribe(topicsList);
                System.out.println(String.format(
                        "%s, %s",
                        messageConsumer.getGroupId(),
                        messageConsumer.getTopics().toString()));

                // TODO: consumerReceive
                break;

            default:
                throw new IllegalArgumentException("Need to specify 'prod' or 'cons'");
        }


    }
}
