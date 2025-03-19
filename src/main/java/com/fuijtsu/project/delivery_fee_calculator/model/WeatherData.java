package com.fuijtsu.project.delivery_fee_calculator.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationName;
    private String wmoCode;
    private double airTemp;
    private double windSpeed;
    private String weatherPhenomenon;
    private LocalDateTime timestamp;

    public WeatherData() {}

    public WeatherData(String stationName, String wmoCode, double airTemp, double windSpeed, String weatherPhenomenon, LocalDateTime timestamp) {
        this.stationName = stationName;
        this.wmoCode = wmoCode;
        this.airTemp = airTemp;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timestamp = timestamp;
    }

    // Getters & Setters

}
