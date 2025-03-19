package com.fuijtsu.project.delivery_fee_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // CronJob
public class DeliveryFeeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
	}

}
