package com.hejula.server.dto;

import com.hejula.server.entities.Accommodation;
import com.hejula.server.entities.Customer;
import com.hejula.server.entities.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private long accommodationSeq;

    private long customerSeq;

    private Date checkinDate;

    private Date checkoutDate;

    private long adult;

    private long children;


    
    private long scheduleSeq;
    
    private String reservationNo;
    
    

    public Schedule convertToSchedule(){ //scheduleSeq, reservationNo 제외
        Schedule schedule = new Schedule().builder()
                .accommodation(new Accommodation().builder().accommodationSeq(this.accommodationSeq).build())
                .customer(new Customer().builder().customerSeq(this.customerSeq).build())
                .checkinDate(this.checkinDate)
                .checkoutDate(this.checkoutDate)
                .adult(this.adult)
                .children(this.children)
                .build();

        return schedule;
    }


}
