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
@Table(name = "wish_list")
public class WishList {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long wishListSeq;

  @ManyToOne(targetEntity = Customer.class)
  @JoinColumn(name = "customer_seq")
  private long customerSeq;

  @ManyToOne(targetEntity = Accommodation.class)
  @JoinColumn(name = "accommodation_seq")
  private long accommodationSeq;

}
