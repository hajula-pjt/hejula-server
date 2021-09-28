package com.hejula.server.service;

import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.entities.Accommodation;
import com.hejula.server.entities.Gu;
import com.hejula.server.repository.AccommodationRepository;
import com.hejula.server.repository.GuRepository;
import com.hejula.server.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jooyeon
 * @since 2021.07.17
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationService {

    private final GuRepository guRepository;
    private final AccommodationRepository accommodationRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Gu> getGuList(String name){
        List<Gu> list = guRepository.findByNameContaining(name);
        return list;
    }

   public Page<Accommodation> getAccommodationList(AccommodationSearchDto asDto) throws IllegalAccessException {
//   public List<Accommodation> getAccommodationList(String gu, Date checkIn, Date checkOut, int people, int page, int rows){
        return accommodationRepository.getAccommodationByGuAndSchedule(asDto, PageRequest.of(asDto.getPage(), asDto.getRows()));
    }

    public Accommodation getAccommodationDetail(long accommodationSeq) {
        return accommodationRepository.findById(accommodationSeq).orElseThrow(IllegalAccessError::new);
    }

    public void setView(long accommodationSeq) {
        Accommodation accommodation = accommodationRepository.findById(accommodationSeq).orElseThrow(IllegalArgumentException::new);
        log.info("in setView accommodation : {}", accommodation.getName());
        log.info("view : {}", accommodation.getViews());

        long view = accommodation.getViews() + 1;
        accommodation.setViews(view);
        accommodationRepository.save(accommodation);
    }

    public void addVisitors(long accommodationSeq) {
        Accommodation accommodation = accommodationRepository.getById(new Long(accommodationSeq));
        long visitors = accommodation.getVisitors() + 1;
        accommodation.setVisitors(visitors);
        accommodationRepository.save(accommodation);
    }
}
