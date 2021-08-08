package com.hejula.server.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long reviewSeq;

  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;

  @ManyToOne(targetEntity = Schedule.class)
  @JoinColumn(name = "schedule_seq")
  private long scheduleSeq;

  @Column(nullable = false)
  private String comment;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:mm:ss", timezone = "Asia/Seoul")
  private java.sql.Date registDate;


}
