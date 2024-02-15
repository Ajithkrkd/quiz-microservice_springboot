package com.ajith.notificationservice.consumer;

import com.ajith.notificationservice.consumer.service.MailService;
import com.ajith.notificationservice.event.QuizSubmitEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
public class Listener {

    private final MailService service;
   @KafkaHandler
    public void consumeJsonMsg(QuizSubmitEvent message) throws MessagingException, UnsupportedEncodingException {
        service.sendMail ( message );
        log.info("Consuming the message from notificationTopic: {}", message.toString());
    }
}
