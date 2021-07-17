package com.hejula.server.entities;


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
@Table(name = "schedule")
public class Schedule {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long scheduleSeq;

  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;

  @ManyToOne(targetEntity = Customer.class)
  @JoinColumn(name = "customer_seq")
  private long customerSeq;

  @Column(nullable = false)
  private Date checkinDate;

  @Column(nullable = false)
  private Date checkoutDate;

  @Column(nullable = false)
  private long adult;

  @Column(nullable = false)
  private long children;

  private String reservationNo;

}
