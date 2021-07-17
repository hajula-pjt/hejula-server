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
@Table(name = "statistics")
public class Statistics {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long statisticsSeq;

  @ManyToOne(targetEntity = Admin.class)
  @JoinColumn(name = "admin_seq")
  private long adminSeq;

  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;


  private long teens;
  private long twenties;
  private long thirties;
  private long fourties;
  private long fifties;
  private double averageRating;
  private double bestRating;
  private long maxReviewSeq;
  private double worstRating;
  private long minReviewSeq;
  private long janVisitors;
  private long febVisitors;
  private long marVisitors;
  private long aprVisitors;
  private long mayVisitors;
  private long junVisitors;
  private long julVisitors;
  private long augVisitors;
  private long sepVisitors;
  private long octVisitors;
  private long novVisitors;
  private long decVisitors;

}