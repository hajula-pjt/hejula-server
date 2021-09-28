package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 평점 DTO
 *
 * @author jooyeon
 * @since 2021.08.29
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class StarsDto {

    private long bestReveiwSeq;

    private double bestRating;

    private long worstReveiwSeq;

    private double worstRating;

}
