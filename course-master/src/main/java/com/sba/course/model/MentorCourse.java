package com.sba.course.model;

import lombok.Data;

import java.sql.Date;

@Data
public class MentorCourse {
  private Integer id;
  private String name;
  private String description;
  private String skill;
  private Date startDate;
  private Date endDate;
  private String mentorName;
  private Float fee;
  private Integer rate;
  private Integer duration;
}
