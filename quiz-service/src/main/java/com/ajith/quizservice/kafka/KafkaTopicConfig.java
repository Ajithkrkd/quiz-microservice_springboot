package com.ajith.quizservice.kafka;


import com.ajith.quizservice.event.QuizSubmitEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {
  private final  KafkaConnectionDetails kafkaProperties;
    @Bean
    public KafkaTemplate <String, QuizSubmitEvent > kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory <String, QuizSubmitEvent> producerFactory() {
        Map <String, Object> configProps = new HashMap <> ();

        configProps.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:29092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory <> (configProps);
    }
    @Bean
    public NewTopic notificationTopic()
    {
        return TopicBuilder
                .name ( "notificationTopic" )
                .build ();
    }
}
