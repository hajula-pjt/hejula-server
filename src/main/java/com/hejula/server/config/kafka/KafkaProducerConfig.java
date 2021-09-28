package com.hejula.server.config.kafka;


import com.hejula.server.dto.ScheduleDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;

    @Bean
    public ProducerFactory<String, ScheduleDto> ScheduleDtoProducerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all"); //acks=all, min.insync.replicas=2
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); //메시지를 중복으로 보내지 않도록 설정
        configProps.put(ProducerConfig.RETRIES_CONFIG, 5); //재시도 횟수 - ENABLE_IDEMPOTENCE_CONFIG을 사용하기 위해서는 0보다 커야 함
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ScheduleDto> ScheduleDtoKafkaTemplate(){
        return new KafkaTemplate<>(ScheduleDtoProducerFactory());
    }

    @Bean
    public ProducerFactory<String, String> CommonProducerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 5);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> CommonKafkaTemplate(){
        return new KafkaTemplate<>(CommonProducerFactory());
    }

}
