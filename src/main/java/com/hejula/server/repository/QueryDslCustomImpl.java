package com.hejula.server.repository;

import com.hejula.server.dto.AccommodationReportDto;
import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.dto.WeeklyCustomer;
import com.hejula.server.entities.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryDslCustomImpl implements QueryDslCustom{

    private final JPAQueryFactory jpaQueryFactory;

    QAccommodation accommodationEntity = QAccommodation.accommodation;
    QSchedule scheduleEntity = QSchedule.schedule;
    QPrice priceEntity = QPrice.price1;
    QAdmin adminEntity = QAdmin.admin;

    @Override
    public Page<Accommodation> getAccommodationByGuAndSchedule(AccommodationSearchDto asDto, Pageable pageable) throws IllegalAccessException {
        QueryResults<Accommodation> result = null;

        switch (asDto.getSearchType()){
            case "VIEW":
                result = jpaQueryFactory
                        .selectFrom(accommodationEntity)
                        .where(accommodationEntity.max.goe(asDto.getPeople())
                                .and(accommodationEntity.gu().guSeq.eq(asDto.getGuSeq()))
                                .and(Expressions.asDateTime(asDto.getCheckIn()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                                .and(Expressions.asDateTime(asDto.getCheckOut()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                        )
                        .innerJoin(accommodationEntity).on(scheduleEntity.accommodation().accommodationSeq.eq(accommodationEntity.accommodationSeq))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(accommodationEntity.views.desc())
                        .fetchResults();
                break;
            case "VISITOR":
                result = jpaQueryFactory
                        .selectFrom(accommodationEntity)
                        .where(accommodationEntity.max.goe(asDto.getPeople())
                                .and(accommodationEntity.gu().guSeq.eq(asDto.getGuSeq()))
                                .and(Expressions.asDateTime(asDto.getCheckIn()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                                .and(Expressions.asDateTime(asDto.getCheckOut()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                        )
                        .innerJoin(accommodationEntity).on(scheduleEntity.accommodation().accommodationSeq.eq(accommodationEntity.accommodationSeq))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(accommodationEntity.visitors.desc())
                        .fetchResults();
                break;
            case "LOWPRICE":
               result = jpaQueryFactory
                        .selectFrom(accommodationEntity)
                        .where(accommodationEntity.max.goe(asDto.getPeople())
                                .and(accommodationEntity.gu().guSeq.eq(asDto.getGuSeq()))
                                .and(Expressions.asDateTime(asDto.getCheckIn()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                                .and(Expressions.asDateTime(asDto.getCheckOut()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                        )
                        .innerJoin(accommodationEntity).on(scheduleEntity.accommodation().accommodationSeq.eq(accommodationEntity.accommodationSeq))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(accommodationEntity.yearAveragePrice.desc()) //평균가격으로 비교 필요
                        .fetchResults();
                break;
            case "HIGHPRICE":
                result = jpaQueryFactory
                        .selectFrom(accommodationEntity)
                        .where(accommodationEntity.max.goe(asDto.getPeople())
                                .and(accommodationEntity.gu().guSeq.eq(asDto.getGuSeq()))
                                .and(Expressions.asDateTime(asDto.getCheckIn()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                                .and(Expressions.asDateTime(asDto.getCheckOut()).notBetween(accommodationEntity.schedules.any().checkinDate, accommodationEntity.schedules.any().checkoutDate))
                        )
                        .innerJoin(accommodationEntity).on(scheduleEntity.accommodation().accommodationSeq.eq(accommodationEntity.accommodationSeq))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(accommodationEntity.yearAveragePrice.asc()) //평균가격으로 비교 필요
                        .fetchResults();
                break;
            default :
                throw new IllegalAccessException();
        }

       return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public boolean existSchedule(Schedule schedule) {
        Integer resultOne = jpaQueryFactory
                .selectOne()
                .from(scheduleEntity)
                .where(scheduleEntity.accommodation().accommodationSeq.eq(schedule.getAccommodationSeq())
                        .and(scheduleEntity.checkinDate.between(schedule.getCheckinDate(), schedule.getCheckoutDate())
                            .or((scheduleEntity.checkoutDate.between(schedule.getCheckinDate(), schedule.getCheckoutDate()))))
                )
                .fetchOne();

        return resultOne == null ? false : true;
    }

    @Override
    public List<Schedule> getScheduleByDate(long accommodationSeq, Date startDate, Date endDate) {
        return jpaQueryFactory
                .selectFrom(scheduleEntity)
                .where(scheduleEntity.accommodation().accommodationSeq.eq(accommodationSeq)
                        .and((scheduleEntity.checkinDate.between(startDate, endDate)
                            .or((scheduleEntity.checkoutDate.between(startDate, endDate))))
                        )
                )
                .fetch();
    }

    @Override
    public List<Price> getPrice(long accommodationSeq, Date startDate, Date endDate) {
        return jpaQueryFactory
                .selectFrom(priceEntity)
                .where(priceEntity.accommodation().accommodationSeq.eq(accommodationSeq)
                        .and(priceEntity.fullDay.between(startDate, endDate))
                )
                .fetch();
    }

    @Override
    public List<WeeklyCustomer> getWeeklyCustomer(Date fromDate, Date toDate, String adminId) {
        /*Date date = new Date();
        long sum = 0;

        jpaQueryFactory
                .select(Projections.constructor(WeeklyCustomer.class,
                        date, sum
                        ))
                .from(accommodationEntity)
                .leftJoin(adminEntity).on(adminEntity.adminSeq.eq(accommodationEntity.admin().adminSeq))
                .where(adminEntity.id.eq(adminId)
                        .and(accommodationEntity.schedules.)
                )
                .groupBy(fromDate)
                .fetch();*/

        return null;
    }

    @Override
    public Page<AccommodationReportDto> getMonthlyAccommodationReport(Date start, Date end, String adminId, Pageable pageable) {
      QueryResults<AccommodationReportDto> result = null;

     result = jpaQueryFactory
                .select(Projections.constructor(AccommodationReportDto.class
                    , scheduleEntity.accommodation().name
                    , scheduleEntity.customer().nickname
                    , scheduleEntity.adult
                    , scheduleEntity.children
                    , scheduleEntity.checkinDate
                    , scheduleEntity.checkoutDate
                ))
                .from(scheduleEntity)
                .where(scheduleEntity.accommodation().admin().id.eq(adminId)
                        .and(scheduleEntity.checkinDate.between(start, end))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(scheduleEntity.checkinDate.asc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }


}
