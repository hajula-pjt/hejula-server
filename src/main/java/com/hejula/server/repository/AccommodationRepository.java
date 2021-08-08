package com.hejula.server.repository;

import com.hejula.server.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, QueryDslCustom{
   /* //
    //    // 해당 구에 선택 가능한 accommodation 검색
//    public Page<Accommodation> findByMaxGreaterThanAndGu_NameContainingAndSchedules_CheckinDateLessThanEqualAndCheckOutDateGreaterThanEqual(int people, String gu, Date checkIn, Date checkOUt,Pageable pageable);

    @Query("select a from Accommodation a left join a.schedules s where a.max >= ?1 and s.checkinDate >= ?3 and s.checkoutDate >= ?4")
    public Page<Accommodation> findBySchedules(int people, String gu, Date checkIn, Date checkOut, Pageable pageable);
//and a.gu.name like CONCAT('%',?2,'%')*/
}