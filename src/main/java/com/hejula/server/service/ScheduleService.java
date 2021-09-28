package com.hejula.server.service;

import com.hejula.server.entities.Schedule;
import com.hejula.server.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public boolean insertSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
        return schedule.getScheduleSeq() > 0 ? true : false;
    }

    public boolean isScheduleByDate(Schedule schedule){
        return scheduleRepository.existSchedule(schedule);
    }

    public List<Schedule> getScheduleByFromToDate(String accommodationSeq, String fromDate, String toDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Date startDate = df.parse(fromDate);
        Date endDate = df.parse(toDate);

    /*    Calendar cal = Calendar.getInstance();

        int year = Integer.parseInt(fromDate.substring(0, 4));
        int month = Integer.parseInt(fromDate.substring(5));
        cal.set(year, month, 1);
        Date startDate = cal.getTime();

        year = Integer.parseInt(toDate.substring(0, 4));
        month = Integer.parseInt(toDate.substring(5));
        cal.set(year, month, 1);
        Date endDate = cal.getTime();*/

        return scheduleRepository.getScheduleByDate(new Long(accommodationSeq), startDate, endDate);
    }
}
