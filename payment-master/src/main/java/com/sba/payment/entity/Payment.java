package com.sba.payment.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payment")
@Data
public class Payment {
	@Id
	private Integer id;
	private Integer courseId;
	private String userName;
	private String mentorName;
	private Float cost;

}
