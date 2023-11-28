package com.block15kafka.configuration.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestListener {

    @KafkaListener(topics = "${message.topic.name:topickafka}", groupId = "${message.group.name:topicgroup}")
    public void listenTopic1(String message) {
        System.out.println("Recieved Message of topic1 in  listener: " + message);
    }

    @KafkaListener(topics = "${message.topic.name2:topickafka2}", groupId = "${message.group.name:topicgroup}")
    public void listenTopic2(String message) {
        System.out.println("Recieved Message of topic2 in  listener "+message);
    }
}
