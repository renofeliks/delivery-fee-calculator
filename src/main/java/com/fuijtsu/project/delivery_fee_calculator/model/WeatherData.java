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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    public double getAirTemperature() {
        return airTemp;
    }

    public void setAirTemperature(double airTemperature) {
        this.airTemp = airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", stationName='" + stationName + '\'' +
                ", wmoCode='" + wmoCode + '\'' +
                ", airTemperature=" + airTemp +
                ", windSpeed=" + windSpeed +
                ", weatherPhenomenon='" + weatherPhenomenon + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
