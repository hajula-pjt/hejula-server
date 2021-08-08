package com.hejula.server.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "price")
public class Price {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long priceSeq;

  @ManyToOne(targetEntity = Schedule.class)
  @JoinColumn(name = "schedule_seq")
  private long scheduleSeq;

  @Column(nullable = false)
  private long accommodationSeq;

  @Column(nullable = false)
  private long month;

  @Column(nullable = false)
  private long day;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd", timezone = "Asia/Seoul")
  private Date date;

  @Column(nullable = false)
  private long price;

}
