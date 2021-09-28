package com.hejula.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 숙소 검색을 위한 DTO
 *
 * @author jooyeon
 * @since 2021.08.15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationSearchDto {

    private Long guSeq;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date checkOut;

    private Integer people;

    private Integer page;

    private Integer rows;

    private String searchType; // 타입 //추후 enum으로 변경?

}
