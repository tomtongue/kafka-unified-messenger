package com.tomtan.messenger.kafka;

import com.google.common.base.Strings;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.InvalidOffsetException;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main {
    public static String sysGetProperty(String key) {
        String sysProp = System.getProperty(key);
        if(Strings.isNullOrEmpty(sysProp)) { throw new IllegalArgumentException(String.format("The key: '%s' is required.", key)); }
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
        String topic = sysGetProperty("topic");
        String topics = sysGetProperty("topics");
        List<String> topicsList = Arrays.asList(topics.split(","));

        // TODO: Move to read the external config file (set temporarily), and move this part to test
        Map<String, String> prodMap = new HashMap<>();
        prodMap.put("bootstrap.servers", bootstrapServers);
        prodMap.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        prodMap.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // TODO: Move to read the external config file (set temporarily), and move this part to test
        Map<String, String> consMap = new HashMap<>();
        consMap.put("bootstrap.servers", bootstrapServers);
        consMap.put("group.id", groupId);
        consMap.put("enable.auto.commit", "false");
        consMap.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        consMap.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");



        String inputLog = String.format(
                "mode: %s, specified servers: %s, groupId: %s, topics: %s",
                mode, bootstrapServers, groupId, topicsList.toString());
        logger.info(inputLog);

        switch(mode) {
            case("prod"):
                MessageProducer messageProducer= new MessageProducer(prodMap);
                messageProducer.setClientId(null);
                messageProducer.setTopic(topic);

                String producerTopic = messageProducer.getTopic();
                try {
                    logger.info("Start sending messages...");
                    int seq = 0;
                    while(true) {
                        try {
                            String message = String.format("message from a producer with seq-num: %s", seq);
                            logger.info("Sending a message: " + message);
                            messageProducer.publish(producerTopic, seq, message);
                            seq++;
                            Thread.sleep(1000);
                        } catch (Exception e) { // TODO: Improve this exception handling
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    messageProducer.close();
                }
                break;

            case("cons"):
                MessageConsumer messageConsumer = new MessageConsumer(consMap);
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
                    logger.info("Start polling messages...");
                    while(true) {
                        try {
                            ConsumerRecords<Integer, String> records = messageConsumer.poll(Duration.ofSeconds(5));
                            for (ConsumerRecord<Integer, String> record : records) {
                                logger.info(messageConsumer.returnMessage(record));
                                messageConsumer.offsetCommit(record, messageConsumer.consumer);
                            }
                            Thread.sleep(1000);
                        } catch (InvalidOffsetException ioe) {
                            ioe.printStackTrace();
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
