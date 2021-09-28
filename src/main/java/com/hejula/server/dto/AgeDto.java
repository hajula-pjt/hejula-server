package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 필요한 user 정보만 담아 전송
 *
 * @author jooyeon
 * @since 2021.08.20
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AgeDto {

    private String age;

    private long num;



}
