package com.hejula.server.repository;

import com.hejula.server.dto.AccommodationReportDto;
import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.dto.WeeklyCustomer;
import com.hejula.server.entities.Accommodation;
import com.hejula.server.entities.Price;
import com.hejula.server.entities.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface QueryDslCustom {

    /* 예약 가능한 숙소 조회 */
    public Page<Accommodation> getAccommodationByGuAndSchedule(AccommodationSearchDto asDto, Pageable pagable) throws IllegalAccessException;

    /* 리뷰 조회 */

    /* 예약 가능한 날짜인지 확인 */
    public boolean existSchedule(Schedule schedule);

    /* 연월에 해당하는 스케줄 조회 */
    public List<Schedule> getScheduleByDate(long accommodationSeq, Date startDate, Date endDate);

    /* 기간에 해당하는 가격 조회 */
    public List<Price> getPrice(long accommodationSeq, Date startDate, Date endDate);

    /* 금주 투숙객 현황 */
    public List<WeeklyCustomer> getWeeklyCustomer(Date fromDate, Date toDate, String adminId);

    /* 월별 숙박 현황 */
    public Page<AccommodationReportDto> getMonthlyAccommodationReport(Date start, Date end, String adminId, Pageable pageable);
}
