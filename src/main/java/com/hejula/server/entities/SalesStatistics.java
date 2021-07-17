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
@Table(name = "sales_statistics")
public class SalesStatistics {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long salesStatisticsSeq;

  @ManyToOne(targetEntity = Admin.class)
  @JoinColumn(name = "admin_seq")
  private long adminSeq;

  @Column(nullable = false)
  private long year;

  @Column(nullable = false)
  private long month;

  @Column(nullable = false)
  private long day;

  @Column(nullable = false)
  private long week;


}
