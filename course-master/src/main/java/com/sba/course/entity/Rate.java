package com.sba.course.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rate")
@Data
public class Rate {

  @Id
  private Integer id;
  private Integer cuorseId;
  private Integer rating;

}
