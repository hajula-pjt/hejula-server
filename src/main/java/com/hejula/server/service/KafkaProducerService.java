package com.hejula.server.service;

import com.hejula.server.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
@EnableRetry
@Slf4j
public class KafkaProducerService {

    @Value("${consumer.reservation.topic}")
    private String RESERVATION_TOPIC;

    @Value("${consumer.view.topic}")
    private String VIEW_TOPIC;

    private final AccommodationService accommodationService;
    private final UserService userService;
    private final KafkaTemplate<String, ScheduleDto> scheduleTemplate;
    private final KafkaTemplate<String, String> commonTemplate;

    //Exception.class에 대한 모든 걸 retry
    //최대횟수 10
    //보내는 간격 사이 delay 1초
   // @Retryable(value=Exception.class, maxAttempts = 10, backoff = @Backoff(delayExpression = "1000"))
    public void reservation(ScheduleDto scheduleDto){
        log.info("{}", scheduleDto);

        ListenableFuture<SendResult<String, ScheduleDto>> future = scheduleTemplate.send(RESERVATION_TOPIC, scheduleDto);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ScheduleDto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(scheduleDto.getCustomerSeq() + "'s " + scheduleDto.getAccommodationSeq()+" request sent failed");
                log.error(throwable.getMessage());
                ProducerRecord<?, ?> producerRecord = ((KafkaProducerException) throwable).getFailedProducerRecord();
                throw new KafkaProducerException(producerRecord, throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, ScheduleDto> stringScheduleDtoSendResult) {
                log.info(scheduleDto.getCustomerSeq() + "'s " + scheduleDto.getAccommodationSeq()+" request sent success");
            }
        });
    }

    //view 수  증가
    public void setViewPlus(String accommodationSeq) {
        log.info("into setViewPlus, seq = {}", accommodationSeq);

        ListenableFuture<SendResult<String, String>> future = commonTemplate.send(VIEW_TOPIC, accommodationSeq);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(accommodationSeq+ " request sent failed");
                log.error(throwable.getMessage());
                ProducerRecord<?, ?> producerRecord = ((KafkaProducerException) throwable).getFailedProducerRecord();
                throw new KafkaProducerException(producerRecord, throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info(accommodationSeq+ " request sent success");
            }
        });
    }
}
