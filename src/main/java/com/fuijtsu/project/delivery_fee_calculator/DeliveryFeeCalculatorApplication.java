package com.fuijtsu.project.delivery_fee_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for the Spring Boot application.
 * This class bootstraps the application and enables scheduling support.
 */
@SpringBootApplication
@EnableScheduling // For CronJob
public class DeliveryFeeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
	}

}
