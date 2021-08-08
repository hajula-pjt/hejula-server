/*
package com.hejula.server.controller;

import com.hejula.server.entities.Accommodation;
import com.hejula.server.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/publishTest")
    public boolean testAccommodation(Accommodation accommodation){
        return kafkaProducerService.test(accommodation);
    }

}
*/
