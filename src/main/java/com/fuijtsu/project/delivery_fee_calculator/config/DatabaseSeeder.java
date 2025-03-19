package com.fuijtsu.project.delivery_fee_calculator.config;

import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DatabaseSeeder {
    @Bean
    CommandLineRunner initDatabase(WeatherDataRepository repository) {
        return args -> {
            repository.save(new WeatherData("Tallinn-Harku", "26038", -5.0, 8.0, "Light snow", LocalDateTime.now()));
            repository.save(new WeatherData("Tartu-Tõravere", "26242", -2.1, 4.7, "Light snow shower", LocalDateTime.now()));
            repository.save(new WeatherData("Pärnu", "41803", 1.5, 3.2, "Cloudy", LocalDateTime.now()));
        };
    }
}
