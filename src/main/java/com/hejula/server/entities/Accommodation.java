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
@Table(name = "accommodation")
public class Accommodation {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long accommodationSeq;

  @Column(nullable = false)
  private long adminSeq;

  private String name;

  private String information;

  private String mainImgPath;

  private String address;

  private String detailAddress;

  private String postalCode;

  private String gu;

  private Date checkinTime;

  private Date checkoutTime;

  private String selfCheckinWay;

  private double rating;

  private long views;

  private long visitors;

  private long max;

  private long bedroom;

  private long bathroom;

  @Column(nullable = false)
  private long xCoordinate;

  @Column(nullable = false)
  private long yCoordinate;

  private Date registDate;

}
