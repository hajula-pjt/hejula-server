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
@Table(name = "image")
public class Image {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long imageSeq;

  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;

  @Column(nullable = false)
  private String path;

  @Column(nullable = false)
  private long sort;

}
