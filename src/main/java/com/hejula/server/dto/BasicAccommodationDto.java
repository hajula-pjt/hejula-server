package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 단순 숙소 정보만을 위한 DTO
 *
 * @author jooyeon
 * @since 2021.08.29
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicAccommodationDto {

    private long accommodationSeq;

    private String name;

    private String information;
}
