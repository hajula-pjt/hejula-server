package com.hejula.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

        @ApiOperation(value = "This method is used to get the clients.")
        @GetMapping(value="/test")
        public List<String> getClients() {
            return Arrays.asList("First Client", "Second Client");
        }

}
