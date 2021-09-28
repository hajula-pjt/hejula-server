package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대시보드 이번 달 데이터를 위한 DTO
 *
 * @author jooyeon
 * @since 2021.07.17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyStatisticsDto {

    private long visitors; //방문객

    private double sales; //매출액

    private long rateOperation; //가동률

}
