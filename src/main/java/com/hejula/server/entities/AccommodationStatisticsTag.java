package com.hejula.server.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accommodation_statistics_tag")
public class AccommodationStatisticsTag {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long statisticsTagSeq;

  @ManyToOne(targetEntity = Statistics.class)
  @JoinColumn(name = "statistics_seq")
  private long statisticsSeq;

  private String name;

}
