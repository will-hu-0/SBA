package com.sba.course.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="course")
@Data
public class Course {
  @Id
  private Integer id;
  private String name;
  private String description;
  private String skill;
  private Date startDate;
  private Date endDate;
  private String mentorName;
  private Integer progress;
  private Float fee;
  private String status;
  private String userName;
}
