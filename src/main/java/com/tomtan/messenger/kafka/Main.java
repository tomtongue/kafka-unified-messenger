package com.tomtan.messenger.kafka;

import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.*;

import kafka.common.KafkaException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.InvalidOffsetException;

public class Main {
    public static String sysGetProperty(String key) {
        String sysProp = System.getProperty(key);
        if(StringUtils.isEmpty(sysProp)) { throw new IllegalArgumentException(String.format("The key: '%s' is required.", key)); }
        return sysProp;
    }
    public static void main(String[] args) {
        // Currently, just using `mode` and `topics` parameters
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tFT%1$tTZ] [%4$s] %5$s %n"); // TODO: Move to log42j
        Logger logger = Logger.getLogger("com.tomtan.messenger.kafka");
        String mode = sysGetProperty("mode");
        String bootstrapServers = sysGetProperty("servers"); // TODO: Move to mapProps param

        // String clientId = System.getProperty("id");
        String groupId = sysGetProperty("gid");
        // String mapProps = sysGetProperty("props"); // TODO: Implementation
        String topics = sysGetProperty("topics");
        List<String> topicsList = Arrays.asList(topics.split(","));

        // Move this part to each components in the future
        Properties conf = new Properties();
        conf.setProperty("bootstrap.servers", bootstrapServers);
        conf.setProperty("group.id", groupId);
        conf.setProperty("enable.auto.commit", "false");
        conf.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        conf.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        String inputLog = String.format(
                "mode: %s, specified servers: %s, groupId: %s, topics: %s",
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

                String messageConsumerGid = messageConsumer.getGroupId();
                List<String> messageConsumerTopics = messageConsumer.getTopics();
                logger.info(String.format(
                        "clientId: %s, subscribed-topics: %s",
                        messageConsumerGid,
                        messageConsumerTopics.toString()));

                // Subscribe operation
                messageConsumer.subscribe(messageConsumerTopics);
                try {
                    while(true) {
                        try {
                            logger.info("Polling messages...");
                            ConsumerRecords<Integer, String> records = messageConsumer.poll(Duration.ofSeconds(5));
                            for (ConsumerRecord<Integer, String> record : records) {
                                messageConsumer.showMessage(record);
                                messageConsumer.offsetCommit(record, messageConsumer.consumer);
                            }
                            Thread.sleep(2000);
                        } catch (InvalidOffsetException ioe) {
                            ioe.printStackTrace();
                        } catch (KafkaException ke) {
                            ke.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("Need to specify 'prod' or 'cons'");
        }
    }
}
