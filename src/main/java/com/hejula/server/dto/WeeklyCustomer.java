package com.hejula.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 금주 투숙객 현황 데이터를 위한 DTO
 *
 * @author jooyeon
 * @since 2021.08.20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyCustomer {

    private String dayOfWeek; //요일

    private Date date;

    private long visitors;

    public WeeklyCustomer(int dayOfWeek, Date date, long visitors){
        switch (dayOfWeek){
            case 1:
                this.dayOfWeek = "일요일";
                break;
            case 2:
                this.dayOfWeek = "월요일";
                break;
            case 3:
                this.dayOfWeek = "화요일";
                break;
            case 4:
                this.dayOfWeek = "수요일";
                break;
            case 5:
                this.dayOfWeek = "목요일";
                break;
            case 6:
                this.dayOfWeek = "금요일";
                break;
            case 7:
                this.dayOfWeek = "토요일";
                break;
            default:
                throw new IllegalArgumentException();
        }

        this.date = date;
        this.visitors = visitors;
    }

}
