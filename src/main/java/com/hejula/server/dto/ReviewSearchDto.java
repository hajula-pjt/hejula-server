package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 숙소조회 DTO
 *
 * @author jooyeon
 * @since 2021.09.05
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSearchDto {

    private long accommodationSeq;

    private String keyword;

    private int rows;

    private int pageNo;

}
