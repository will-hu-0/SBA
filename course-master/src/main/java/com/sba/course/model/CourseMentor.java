package com.sba.course.model;

import lombok.Data;

import java.util.Date;

@Data
public class CourseMentor {

    private Integer id;
    private String name;
    private String skill;
    private Date startDate;
    private Date endDate;
    private String status;
    private String userName;
    private Float cost;
    private Float rate;
    private String description;
    private Integer duration;
    private Integer schedule;

}
