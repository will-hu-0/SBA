package com.sba.course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sba.course.model.Payment;


@FeignClient(name = "sba-payment")
public interface PaymentClient {
	
	@RequestMapping(value = "/payment/api/v1/add", method = RequestMethod.POST)
	ResponseEntity<Object> addPayment( @RequestBody Payment payment);


}
