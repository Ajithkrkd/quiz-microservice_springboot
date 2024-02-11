package com.ajith.notificationservice.consumer;

import com.ajith.notificationservice.event.QuizSubmitEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
public class Listener {
   @KafkaHandler
    public void consumeJsonMsg(QuizSubmitEvent message) {
        log.info("Consuming the message from notificationTopic: {}", message.toString());
    }
}
