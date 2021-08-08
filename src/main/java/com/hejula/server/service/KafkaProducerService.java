package com.hejula.server.service;

import com.hejula.server.dto.ScheduleDto;
import com.hejula.server.entities.Schedule;
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

    @Value("${consumer.topic}")
    private String topic;

    private final KafkaTemplate<String, Schedule> template;

    //Exception.class에 대한 모든 걸 retry
    //최대횟수 10
    //보내는 간격 사이 delay 1초
   // @Retryable(value=Exception.class, maxAttempts = 10, backoff = @Backoff(delayExpression = "1000"))
    public Boolean reservation(ScheduleDto scheduleDto){
        Schedule schedule = scheduleDto.convertToSchedule();
        ListenableFuture<SendResult<String, Schedule>> future = template.send(topic, schedule);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Schedule>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(schedule.getCustomer().getCustomerSeq() + "'s " + schedule.getAccommodation().getAccommodationSeq()+" request sent failed");
                log.error(throwable.getMessage());
                ProducerRecord<?, ?> producerRecord = ((KafkaProducerException) throwable).getFailedProducerRecord();
                throw new KafkaProducerException(producerRecord, throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Schedule> stringScheduleSendResult) {
                log.info(schedule.getCustomer().getCustomerSeq() + "'s " + schedule.getAccommodation().getAccommodationSeq()+" request sent success");
            }
        });

        return true;
    }
 }
