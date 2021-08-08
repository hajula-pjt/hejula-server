package com.hejula.server.config.kafka;

import com.hejula.server.entities.Schedule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/*
@EnableKafka annotation is required on the configuration class to enable detection of
@KafkaListener annotation on spring-managed beans.

For consuming messages, we need to configure a ConsumerFactory and a KafkaListenerContainerFactory.
Once these beans are available in the Spring bean factory,
POJO-based consumers can be configured using @KafkaListener annotation.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    @Value("${consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Schedule> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
//                new JsonDeserializer<>(Schedule.class, false));
        return new DefaultKafkaConsumerFactory<>(props);
    }

  /*  @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Schedule> kafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, Schedule> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);//찾아보고 재설정 필요
        factory.setConsumerFactory(consumerFactory());
        return factory;

   }*/


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Schedule> kafkaListenerContainerFactory(
            KafkaTemplate<String, Schedule> template){

        ConcurrentKafkaListenerContainerFactory<String, Schedule> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);//찾아보고 재설정 필요
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template)));
        return factory;

    }
}
