package com.fuijtsu.project.delivery_fee_calculator.service;

import com.fuijtsu.project.delivery_fee_calculator.dto.DeliveryFeeRequest;
import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryFeeService {
    private final WeatherDataRepository repository;

    public DeliveryFeeService(WeatherDataRepository repository) {
        this.repository = repository;
    }

    public double calcDeliveryFee(DeliveryFeeRequest request) {
        Optional<WeatherData> latestWeather = repository.findTopByStationNameOrderByTimestampDesc(getStationName(request.getCity()));

        if (latestWeather.isEmpty()) throw new RuntimeException("Weather data unavailable for city: " + request.getCity());

        WeatherData weather = latestWeather.get();
        double baseFee = getBaseFee(request.getCity(), request.getVehicleType());
        double finalFee = baseFee;

        finalFee += applyTempFee(weather.getAirTemp(), request.getVehicleType());
        finalFee += applyWindFee(weather.getWindSpeed(), request.getVehicleType());
        finalFee += applyWeatherPhenomenonFee(weather.getWeatherPhenomenon(), request.getVehicleType());

        return finalFee;
    }

    private String getStationName(String city) {
        return switch (city.toLowerCase()) {
            case "tallinn" -> "Tallinn-Harku";
            case "tartu" -> "Tartu-Tõravere";
            case "pärnu" -> "Pärnu";
            default -> throw new IllegalArgumentException("Invalid city: " + city);
        };
    }

    private double getBaseFee(String city, String vehicleType) {
        return switch (city.toLowerCase()) {
            case "tallinn" -> vehicleType.equalsIgnoreCase("Car") ? 4.0 : vehicleType.equalsIgnoreCase("Scooter") ? 3.5 : 3.0;
            case "tartu" -> vehicleType.equalsIgnoreCase("Car") ? 3.5 : vehicleType.equalsIgnoreCase("Scooter") ? 3.0 : 2.5;
            case "pärnu" -> vehicleType.equalsIgnoreCase("Car") ? 3.0 : vehicleType.equalsIgnoreCase("Scooter") ? 2.5 : 2.0;
            default -> throw new IllegalArgumentException("Invalid city: " + city);
        };
    }

    private double applyTempFee(double temp, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Scooter") || vehicleType.equalsIgnoreCase("Bike")) {
            if (temp < -10) return 1.0;
            if (temp < 0) return 0.5;
        }
        return 0.0;
    }

    private double applyWindFee(double windSpeed, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Bike")) {
            if (windSpeed >= 20) throw new RuntimeException("Usage of selected vehicle type is forbidden");
            if (windSpeed >= 10) return 1.0;
        }
        return 0.0;
    }

    private double applyWeatherPhenomenonFee(String weatherPhenomenon, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Bike") || vehicleType.equalsIgnoreCase("Scooter")) {
            if (weatherPhenomenon.toLowerCase().contains("snow") || weatherPhenomenon.toLowerCase().contains("sleet")) return 1.0;
            if (weatherPhenomenon.toLowerCase().contains("rain")) return 0.5;
            if (weatherPhenomenon.toLowerCase().contains("storm")) throw new RuntimeException("Usage of selected vehicle type is forbidden");
        }
        return 0.0;
    }
}
