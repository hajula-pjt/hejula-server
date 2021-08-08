package com.hejula.server.repository;

import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.entities.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslCustom {

    /* 예약 가능한 숙소 조회 */
    public Page<Accommodation> getAccommodationByGuAndSchedule(AccommodationSearchDto asDto, Pageable pagable);
//    public List<Accommodation> getAccommodationByGuAndSchedule(String gu, Date checkIn, Date checkOut, int people, Pageable pagable);
}
