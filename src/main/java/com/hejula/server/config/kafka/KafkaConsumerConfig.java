package com.hejula.server.config.kafka;

import com.hejula.server.dto.ScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ListenerUtils;
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
@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;

    @Value("${consumer.group-id}")
    private String GROUP_ID;

    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public ConsumerFactory<String, ScheduleDto> ScheduleDtoConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); //2.3버전부터 default값
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1); //test시 1로 설정
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ScheduleDto.class));
    }


    @Bean
    // 일반 String값 보낼 때 사용하는 factory
    public ConcurrentKafkaListenerContainerFactory<String, String> CommonKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        //MANUAL_IMMEDIATE: Commit the offset immediately when the Acknowledgment.acknowledge() method is called by the listener.
        //https://docs.spring.io/spring-kafka/reference/html/#committing-offsets
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setErrorHandler(new SeekToCurrentErrorHandler((rec, ex) -> log.error(ListenerUtils.recordToString(rec, true) + "\n"
                + ex.getMessage())));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ScheduleDto> ScheduleKafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, ScheduleDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ScheduleDtoConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setErrorHandler(new SeekToCurrentErrorHandler((rec, ex) -> log.error(ListenerUtils.recordToString(rec, true) + "\n"
                + ex.getMessage())));
        return factory;

    }


}
