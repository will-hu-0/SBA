package com.sba.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SbaPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbaPaymentApplication.class, args);
	}

}
