package com.sba.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sba-account")
public interface AccountServiceClient {
	
	@RequestMapping(value = "/account/api/v1/query", method = RequestMethod.GET)
	ResponseEntity<Object> getUser(@RequestParam("username") String username);

}
