package com.hejula.server.repository;

import com.hejula.server.entities.AccommodationStatisticsTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface AccommodationStatisticsTagRepository extends JpaRepository<AccommodationStatisticsTag, Long> {

    public List<AccommodationStatisticsTag> findTop8ByAccommodationSeqOrderByScore(long accommodationSeq);

}