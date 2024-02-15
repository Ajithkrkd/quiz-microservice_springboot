package com.ajith.quizservice.kafka;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {
    @Bean
    public NewTopic notificationTopic()
    {
        return TopicBuilder
                .name ( "notificationTopic" )
                .build ();
    }
}
