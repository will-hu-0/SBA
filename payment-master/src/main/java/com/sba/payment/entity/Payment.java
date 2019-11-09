package com.sba.payment.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment")
@Data
public class Payment {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer courseId;
	private String userName;
	private String mentorName;
	private Float cost;
	private Date startDate;
	private Date endDate;
	private Float fee;
}
