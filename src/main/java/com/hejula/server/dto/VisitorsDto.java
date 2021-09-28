package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 방문객 DTO
 *
 * @author jooyeon
 * @since 2021.08.29
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class VisitorsDto {

   private int month;

   private long num;

}
