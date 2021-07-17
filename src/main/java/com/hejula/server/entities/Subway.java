package com.hejula.server.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


//사용유무..?
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subway")
public class Subway {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long subwaySeq;

  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;


  private String station;

}
