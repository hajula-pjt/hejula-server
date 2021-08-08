package com.hejula.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude = "schedules")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accommodation")
public class Accommodation {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long accommodationSeq;

  @ManyToOne(targetEntity = Admin.class) //Accommodation(N) - Admin(1) 관계
  @JoinColumn(name = "admin_seq")
  private Admin admin;

  @ManyToOne(targetEntity = Gu.class)
  @JoinColumn(name = "gu_seq")
  private Gu gu;

  @JsonBackReference //serialize에서 제외됨
  @OneToMany(mappedBy = "accommodation") //역방향 설정
  private List<Schedule> schedules = new ArrayList<>();

  @JsonBackReference //serialize에서 제외됨
  @OneToMany(mappedBy = "accommodation") //역방향 설정
  private List<FileEntity> files = new ArrayList<>();

  private String name;

  private String information;

  private String address;

  private String detailAddress;

  private String postalCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private Date checkinTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private Date checkoutTime;

  private String selfCheckinWay;

  private double rating;

  @ColumnDefault(value = "0")
  private long views;

  @ColumnDefault(value = "0")
  private long visitors;

  @ColumnDefault(value = "0")
  private long max;

  @ColumnDefault(value = "0")
  private long bedroom;

  @ColumnDefault(value = "0")
  private long bathroom;

  @Column(nullable = false)
  private long xCoordinate;

  @Column(nullable = false)
  private long yCoordinate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:mm:ss", timezone = "Asia/Seoul")
  private Date registDate;

}
