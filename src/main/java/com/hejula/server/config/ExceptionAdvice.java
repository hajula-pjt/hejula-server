package com.hejula.server.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hejula.server.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception을 컨트롤 하기 위함
 *
 * @author jooyeon
 * @since 2021.07.17
 */
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    protected CommonResponse defaultException(Exception e, HttpServletRequest request) throws JsonProcessingException {

        log.error(e.getMessage());

        CommonResponse response = new CommonResponse<>();
        response.setCompleted(false);
        response.setMessage("요청이 실패하였습니다.");
        response.setErrorMessage(e.getMessage());
        return response;

    }

    @ExceptionHandler(KafkaProducerException.class)
    protected CommonResponse KafkaException(KafkaProducerException e, HttpServletRequest request) throws JsonProcessingException {
        log.error(e.getMessage());

        CommonResponse response = new CommonResponse<>();
        response.setCompleted(false);
        response.setMessage("요청이 실패하였습니다.");
        response.setErrorMessage(e.getMessage());
        return response;

    }


}
