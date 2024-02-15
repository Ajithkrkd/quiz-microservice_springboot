package com.ajith.quizservice.kafka;
import com.ajith.quizservice.event.QuizSubmitEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaJsonProducer {
    private final KafkaTemplate<String, QuizSubmitEvent> kafkaTemplate;

    public void sendMessage(QuizSubmitEvent event) {
        try {
            Message<QuizSubmitEvent> message = MessageBuilder
                    .withPayload(event)
                    .setHeader(KafkaHeaders.TOPIC,"notificationTopic")
                    .build();
            kafkaTemplate.send ( message );
            log.info("Message sent successfully to Kafka topic 'notificationTopic'");
        } catch (Exception e) {
            log.error("Error occurred while sending message to Kafka: {}", e.getMessage(), e);
            // Handle the exception as needed
        }
    }
}
