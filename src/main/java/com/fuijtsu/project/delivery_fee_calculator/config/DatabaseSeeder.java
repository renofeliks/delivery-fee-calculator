package com.fuijtsu.project.delivery_fee_calculator.config;

import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Configuration class used to prefill the database with example weather data
 * to test the application's functionality.
 * NB! This method was used for testing purposes only and is not part of the final solution.
 */
@Configuration
public class DatabaseSeeder {
    /**
     * Initializes the database with predefined weather data for the three required stations.
     * @param repository - WeatherDataRepository used to save the entires.
     * @return - CommandLineRunner that seeds the database on application startup.
     */
//    @Bean
//    CommandLineRunner initDatabase(WeatherDataRepository repository) {
//        return args -> {
//            repository.save(new WeatherData("Tallinn-Harku", "26038", -5.0, 8.0, "Light snow", LocalDateTime.now()));
//            repository.save(new WeatherData("Tartu-Tõravere", "26242", -2.1, 4.7, "Light snow shower", LocalDateTime.now()));
//            repository.save(new WeatherData("Pärnu", "41803", 1.5, 3.2, "Cloudy", LocalDateTime.now()));
//        };
//    }
}
