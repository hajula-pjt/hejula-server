package com.hejula.server.repository;

import com.hejula.server.entities.DailyVisitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author jooyeon
 * @since 2021.08.20
 */
@Repository
public interface DailyVisitorsRepository extends JpaRepository<DailyVisitors, Long>,  QueryDslCustom{

    public List<DailyVisitors> getByAccommodation_Admin_IdAndDayBetween(String adminId, Date fromDate, Date toDate);

}


