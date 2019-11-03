package com.sba.course.model;

import lombok.Data;

import java.sql.Date;

@Data
public class UserCourse {
	private Integer id;
	private String name;
	private String description;
	private String skill;
	private Date startDate;
	private Date endDate;
	private String mentorName;
	private String progress;
}
