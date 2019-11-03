package com.sba.auth.model;

import lombok.Data;

@Data
public class SbaUser {
	private Integer id;
	private String username;
	private String password;
	private String role;
	private String status;
}
