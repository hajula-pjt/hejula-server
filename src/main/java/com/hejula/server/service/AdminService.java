package com.hejula.server.service;

import com.hejula.server.dto.AccommodationReportDto;
import com.hejula.server.dto.AgeDto;
import com.hejula.server.dto.StarsDto;
import com.hejula.server.dto.VisitorsDto;
import com.hejula.server.entities.*;
import com.hejula.server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author jooyeon
 * @since 2021.08.20
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final DailyVisitorsRepository dailyVisitorsRepository;
    private final AccommodationRepository accommodationRepository;
    private final StatisticsRepository statisticsRepository;
    private final AccommodationStatisticsTagRepository statisticsTagRepository;

    //이번 달 방문자수, 매출액, 가동률
    public Admin getMonthlyStatistics(String adminId){
        return adminRepository.getById(adminId);
    }

    //금주 투숙객 현황
    public List<DailyVisitors> getWeeklyStatus(String adminId){
        Date today = getTodayWithTime();
        Date start = getStartOfWeek(today);
        Date end = getEndOfWeek(today);
        return dailyVisitorsRepository.getByAccommodation_Admin_IdAndDayBetween(adminId, start, end);
    }

    public Page<AccommodationReportDto> getMonthlyAccommodationReport(String adminId, int rows, int pageNo){
        Date start = getTodayWithTime();

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.DAY_OF_MONTH, 31); //금일 + 30일 까지 노출, 30까지 노출을 위해 1을 더함 (00:00:00으로 시간이 셋팅되기 때문)
        Date end = cal.getTime();

        return accommodationRepository.getMonthlyAccommodationReport(start, end, adminId,  PageRequest.of(pageNo, rows));
    }


    private Date getTodayWithTime(){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        //00:00:00으로설정
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }


    private Date getStartOfWeek(Date date){ //월
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int offset = dayOfWeek - Calendar.MONDAY;
        if(offset > 2) {
            cal.add(Calendar.DAY_OF_MONTH, -offset);
        }else if(offset == 1){
            cal.add(Calendar.DAY_OF_MONTH, -6);
        }

        return cal.getTime();
    }

    private Date getEndOfWeek(Date date){ //일
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int offset = dayOfWeek - Calendar.SUNDAY;
        if(offset > 1) {
            cal.add(Calendar.DAY_OF_MONTH, (7 - offset) + 1); //정확한 end값 검사를 위해 +1 일
        }

        return cal.getTime();
    }

    public Admin getAdmin(Admin admin) {
        return adminRepository.getById(admin.getId());
    }

    public List<AgeDto>  getVisitorsAge(Long accommodationSeq){
        Statistics statistics = statisticsRepository.findByAccommodation_AccommodationSeq(accommodationSeq);
        List<AgeDto> ageList = new ArrayList<>();

        AgeDto age = new AgeDto();
        age.setAge("10");
        age.setNum(statistics.getTeens());
        ageList.add(age);

        age = new AgeDto();
        age.setAge("20");
        age.setNum(statistics.getTwenties());
        ageList.add(age);

        age = new AgeDto();
        age.setAge("30");
        age.setNum(statistics.getThirties());
        ageList.add(age);

        age = new AgeDto();
        age.setAge("40");
        age.setNum(statistics.getFourties());
        ageList.add(age);

        age = new AgeDto();
        age.setAge("50");
        age.setNum(statistics.getFifties());
        ageList.add(age);
        return ageList;
    }

    public List<Accommodation> getAccommodationList(String adminId) {
        return accommodationRepository.findByAdmin_Id(adminId);
    }

    public List<AccommodationStatisticsTag> getVisitorsTag(Long accommodationSeq) {
        return statisticsTagRepository.findTop8ByAccommodationSeqOrderByScore(accommodationSeq);
    }

    public StarsDto getStars(Long accommodationSeq) {
        Statistics statistics = statisticsRepository.findByAccommodation_AccommodationSeq(accommodationSeq);

        StarsDto stars = new StarsDto();
        stars.setBestReveiwSeq(statistics.getMaxReviewSeq());
        stars.setBestRating(statistics.getBestRating());
        stars.setWorstReveiwSeq(statistics.getMinReviewSeq());
        stars.setWorstRating(statistics.getWorstRating());
        return stars;
    }

    public List<VisitorsDto> getVisitors(Long accommodationSeq) {
        Statistics statistics = statisticsRepository.findByAccommodation_AccommodationSeq(accommodationSeq);

        List<VisitorsDto> visitorList = new ArrayList<>();

        VisitorsDto visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(1);
        visitorsDto.setNum(statistics.getJanVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(2);
        visitorsDto.setNum(statistics.getFebVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(3);
        visitorsDto.setNum(statistics.getMarVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(4);
        visitorsDto.setNum(statistics.getAprVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(5);
        visitorsDto.setNum(statistics.getMayVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(6);
        visitorsDto.setNum(statistics.getJunVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(7);
        visitorsDto.setNum(statistics.getJulVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(8);
        visitorsDto.setNum(statistics.getAugVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(9);
        visitorsDto.setNum(statistics.getSepVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(10);
        visitorsDto.setNum(statistics.getOctVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(11);
        visitorsDto.setNum(statistics.getNovVisitors());
        visitorList.add(visitorsDto);

        visitorsDto = new VisitorsDto();
        visitorsDto.setMonth(12);
        visitorsDto.setNum(statistics.getDecVisitors());
        visitorList.add(visitorsDto);

        return visitorList;
    }
}
