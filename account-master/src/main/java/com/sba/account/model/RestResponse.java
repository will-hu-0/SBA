package com.sba.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse<T> {

	private Integer code;
	private String message;
	private T data;

	public RestResponse(Integer code, String message) {
		this.code = code;
		this.message =message;
	}

}
