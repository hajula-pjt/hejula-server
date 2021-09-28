package com.hejula.server.service;

import com.hejula.server.entities.Price;
import com.hejula.server.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jooyeon
 * @since 2021.08.14
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public List<Price> getPrice(String accommodationSeq, String fromDate, String toDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df.parse(fromDate);
        Date endDate = df.parse(toDate);
        return priceRepository.getPrice(new Long(accommodationSeq), startDate, endDate);
    }
}
