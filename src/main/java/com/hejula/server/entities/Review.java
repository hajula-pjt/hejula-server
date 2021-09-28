package com.hejula.server.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties({"accommodation", "schedule"})
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
  private Accommodation accommodation;

  @ManyToOne(targetEntity = Schedule.class)
  @JoinColumn(name = "schedule_seq")
  private Schedule schedule;

  @JsonManagedReference(value = "reviewAndFileReference")
  @OneToMany(mappedBy = "review")
  private List<FileEntity> files = new ArrayList<>();

  @Column(nullable = false)
  private long stars;

  @Column(nullable = false)
  private String comment;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private java.sql.Date registDate;


}
