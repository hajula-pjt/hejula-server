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
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long customerSeq;

  @Column(nullable = false)
  private String id;

  @Column(nullable = false)
  private String passwrod;

  @Column(nullable = false)
  private String birth;

  @Column(nullable = false)
  private long age;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String sex;

  private String profilePath;

}
