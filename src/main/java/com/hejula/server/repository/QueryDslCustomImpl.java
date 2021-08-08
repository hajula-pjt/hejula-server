package com.hejula.server.repository;

import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.entities.Accommodation;
import com.hejula.server.entities.QAccommodation;
import com.hejula.server.entities.QSchedule;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QueryDslCustomImpl implements QueryDslCustom{

    private final JPAQueryFactory jpaQueryFactory;

    QAccommodation accommodationEntity = QAccommodation.accommodation;
    QSchedule scheduleEntity = QSchedule.schedule;

    @Override
    public Page<Accommodation> getAccommodationByGuAndSchedule(AccommodationSearchDto asDto, Pageable pagable) {
        QueryResults<Accommodation> result = jpaQueryFactory
                .selectFrom(accommodationEntity)
                .where(accommodationEntity.max.goe(asDto.getPeople())
                        .and(accommodationEntity.gu().guSeq.eq(asDto.getGuSeq()))
                        .and(Expressions.asDateTime(asDto.getCheckIn()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                        .and(Expressions.asDateTime(asDto.getCheckOut()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                )
                .offset(pagable.getOffset())
                .limit(pagable.getPageSize())
                .fetchResults();


       return new PageImpl<>(result.getResults(), pagable, result.getTotal());
    }
}
