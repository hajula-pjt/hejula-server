package com.hejula.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 금주 숙박 현황 데이터를 위한 DTO
 *
 * @author jooyeon
 * @since 2021.08.20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationReportDto {

    private String accommodationName; // 숙소명

    private String customerNickname; // 고객 닉네임

    private long adult;

    private long children;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date checkinDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date checkoutDate;



    private String num; // 인원 (ex. 3명(어른2 + 어린이1)

    private String schedule; // 숙박 일정 (ex. 2021-06-13 ~ 2021-06-15(2박3일))

    public AccommodationReportDto(String accommodationName, String customerNickname, long adult, long children,
                                  Date checkinDate, Date checkoutDate){
        this.accommodationName = accommodationName;
        this.customerNickname = customerNickname;
        this.adult = adult;
        this.children = children;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

}
