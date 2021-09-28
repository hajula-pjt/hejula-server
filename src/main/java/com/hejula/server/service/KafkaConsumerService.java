package com.hejula.server.service;

import com.hejula.server.dto.ScheduleDto;
import com.hejula.server.entities.Schedule;
import com.hejula.server.exception.NoReservationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService{

    private final ScheduleService scheduleService;
    private final AccommodationService accommodationService;

    @KafkaListener(topics = "${consumer.reservation.topic}", groupId = "${consumer.group-id}", containerFactory = "ScheduleKafkaListenerContainerFactory")
    public void scheduleListener(ScheduleDto scheduleDto, Acknowledgment ack){
//    public void scheduleListener(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition){

        Schedule schedule = scheduleDto.convertToSchedule();
        try{
            log.info("Received message : " + schedule);
            //해당 날짜에 스케줄이 존재하지 않을 경우
            if(!scheduleService.isScheduleByDate(schedule)) {
                scheduleService.insertSchedule(schedule);
                accommodationService.addVisitors(schedule.getAccommodation().getAccommodationSeq());

                //# 중요 : ack.acknowledge()는 모든 비즈니스 로직이 처리된 이후
                // offset 값을 변경하도록 하여 실패에 대한 offset 변경이 발생하지 않도록 비즈니스 로직 제일 마지막에 처리한다.
                ack.acknowledge();
            }else{
                ack.acknowledge();
                throw new NoReservationException("이미 예약이 되어있습니다. 다시 한번 확인 바랍니다.");
            }
        }catch (Exception e){
            ack.acknowledge();
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

    @KafkaListener(topics = "${consumer.view.topic}", groupId = "${consumer.group-id}", containerFactory = "CommonKafkaListenerContainerFactory")
    public void viewListener(String accommodationSeq, Acknowledgment ack){
        log.info("accommodationSeq : {}", accommodationSeq);
        try{
            log.info("Received message : " + accommodationSeq);
            accommodationService.setView(Long.valueOf(accommodationSeq));
            ack.acknowledge();
        }catch (Exception e){
            ack.acknowledge();
            log.info("error : {}", e.getMessage());
        }
    }
}
