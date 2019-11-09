package com.sba.course.model;

import lombok.Data;

import java.util.Date;

@Data
public class Payment {
	
	private Integer id;
	private Integer courseId;
	private String userName;
	private String mentorName;
	private Float cost;
	private Date startDate;
	private Date endDate;
	private Float fee;
}