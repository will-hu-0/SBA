package com.sba.course.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="rate")
@Data
public class Rate {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "cuorseId")
  private Integer cuorseId;
  @Column(name = "rating")
  private Integer rating;

}
