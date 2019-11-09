package com.sba.payment.controller;

import com.sba.payment.model.RestResponse;
import com.sba.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sba.payment.entity.Payment;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> addCoursePay(@RequestBody Payment payment) {
		try {
			paymentRepository.save(payment);
			RestResponse rsp = new RestResponse(200, "OK");
			return new ResponseEntity<>(rsp, HttpStatus.OK);
		} catch (Exception ex) {
			RestResponse rsp = new RestResponse(500, ex.getMessage());
			return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/checkcost/{courseid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getCourseCost(@PathVariable("courseid") Integer courseid) {
		try {
			Float cost = paymentRepository.findById(courseid).get().getCost();

			if (cost != null) {
				Map<String, Float> costdata = new HashMap<>();
				costdata.put("cost", cost);
				RestResponse rsp = new RestResponse(200, "OK");
				return new ResponseEntity<>(rsp, HttpStatus.OK);
			}else {
				Map<String, Float> costdata = new HashMap<>();
				costdata.put("cost", 0F);
				RestResponse rsp = new RestResponse(404, "Not found payment");
				return new ResponseEntity<>(rsp, HttpStatus.OK);
			}
		} catch (Exception ex) {
			RestResponse rsp = new RestResponse(500, ex.getMessage());
			return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
