package com.sba.payment.controller;

import com.sba.payment.model.RestResponse;
import com.sba.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sba.payment.entity.Payment;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> AddCoursePay(@RequestBody Payment payment) {
		try {
			paymentRepository.save(payment);
			RestResponse rsp = new RestResponse(200, "OK");
			return new ResponseEntity<>(rsp, HttpStatus.OK);
		} catch (Exception ex) {
			RestResponse rsp = new RestResponse(500, ex.getMessage());
			return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
