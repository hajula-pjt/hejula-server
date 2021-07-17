package com.sample;


import com.hejula.server.entities.Accommodation;
import com.hejula.server.entities.Statistics;
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
@Table(name = "accommodation_tag")
public class AccommodationTag {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long accommodationTagSeq;


  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;

  private String name;

  private long ranking;

}
