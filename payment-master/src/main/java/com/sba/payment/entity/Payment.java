package com.sba.payment.entity;

import lombok.Data;

import javax.persistence.*;

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

}
