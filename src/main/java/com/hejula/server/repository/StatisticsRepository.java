package com.hejula.server.repository;


import com.hejula.server.entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {


}