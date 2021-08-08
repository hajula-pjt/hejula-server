package com.hejula.server.service;

import com.hejula.server.entities.Schedule;
import com.hejula.server.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService{

    private final ScheduleRepository scheduleRepository;

    @KafkaListener(topics = "${consumer.topic}", groupId = "${consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void scheduleListener(Schedule schedule, Acknowledgment ack){
        try{
            log.info("Received message : " + schedule);
            schedule.getAccommodation().setAccommodationSeq(schedule.getAccommodationSeq());
            schedule.getCustomer().setCustomerSeq(schedule.getCustomerSeq());
            scheduleRepository.save(schedule);
            //# 중요 : ack.acknowledge()는 모든 비즈니스 로직이 처리된 이후
            // //offset 값을 변경하도록 하여 실패에 대한 offset 변경이 발생하지 않도록 비즈니스 로직 제일 마지막에 처리한다.

            ack.acknowledge();
        }catch (Exception e){
            log.info("error : {}", e.getMessage());
        }
    }

      /*  @Override
        @KafkaListener(topics = "${consumer.topic}", groupId = "${consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
        public void onMessage(ConsumerRecord data, Acknowledgment acknowledgment) {
            try {
                log.info("Received message");
                acknowledgment.acknowledge();
            } catch (Exception e) {
                log.error("error : " + e.getMessage());
            }
        }*/

}
