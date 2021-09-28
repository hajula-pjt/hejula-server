package com.hejula.server.repository;

import com.hejula.server.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> , QueryDslCustom{

    public List<Schedule> getByAccommodation_AccommodationSeqAndCheckinDateGreaterThanEqualAndCheckoutDateLessThanEqual(long accommodationSeq, Date startDate, Date endDate);

}