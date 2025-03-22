package com.fuijtsu.project.delivery_fee_calculator.service;

import com.fuijtsu.project.delivery_fee_calculator.dto.DeliveryFeeRequest;
import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class, which is responsible for calculating the delivery fee based on city,
 * vehicle type, and the latest weather conditions.
 */
@Service
public class DeliveryFeeService {
    private final WeatherDataRepository repository;

    public DeliveryFeeService(WeatherDataRepository repository) {
        this.repository = repository;
    }

    /**
     * Calculates delivery fee based on user request.
     * @param request - The request contains city and vehicle type.
     * @return - The calculated delivery fee.
     */
    public double calcDeliveryFee(DeliveryFeeRequest request) {
        // Get latest weather data from the city's weather station.
        Optional<WeatherData> latestWeather = repository.findTopByStationNameOrderByTimestampDesc(getStationName(request.getCity()));
        if (latestWeather.isEmpty()) throw new RuntimeException("Weather data unavailable for city: " + request.getCity());
        WeatherData weather = latestWeather.get();

        // Base fee based on city and vehicle type
        // Additional fees based on weather conditions.
        double deliveryFee = getBaseFee(request.getCity(), request.getVehicleType());
        deliveryFee += applyTempFee(weather.getAirTemp(), request.getVehicleType());
        deliveryFee += applyWindFee(weather.getWindSpeed(), request.getVehicleType());
        deliveryFee += applyWeatherPhenomenonFee(weather.getWeatherPhenomenon(), request.getVehicleType());

        return deliveryFee;
    }

    /**
     * Maps city names to corresponding weather station names.
     * @param city - City name.
     * @return - Corresponding weather station name.
     */
    private String getStationName(String city) {
        return switch (city.toLowerCase()) {
            case "tallinn" -> "Tallinn-Harku";
            case "tartu" -> "Tartu-Tõravere";
            case "pärnu" -> "Pärnu";
            default -> throw new IllegalArgumentException("Invalid city: " + city);
        };
    }

    /**
     * Returns the base fee based on city and vehicle type.
     * @param city - City name.
     * @param vehicleType - Vehicle type used for delivery.
     * @return - Returns base delivery fee.
     */
    private double getBaseFee(String city, String vehicleType) {
        return switch (city.toLowerCase()) {
            case "tallinn" -> vehicleType.equalsIgnoreCase("Car") ? 4.0 : vehicleType.equalsIgnoreCase("Scooter") ? 3.5 : 3.0;
            case "tartu" -> vehicleType.equalsIgnoreCase("Car") ? 3.5 : vehicleType.equalsIgnoreCase("Scooter") ? 3.0 : 2.5;
            case "pärnu" -> vehicleType.equalsIgnoreCase("Car") ? 3.0 : vehicleType.equalsIgnoreCase("Scooter") ? 2.5 : 2.0;
            default -> throw new IllegalArgumentException("Invalid city: " + city);
        };
    }

    /**
     * Adds additional fee based on temperature for scooters and bikes.
     * @param temp - Current air temperature.
     * @param vehicleType - Vehicle type used for delivery.
     * @return - Returns additional temperature fee.
     */
    private double applyTempFee(double temp, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Scooter") || vehicleType.equalsIgnoreCase("Bike")) {
            if (temp < -10) return 1.0;
            if (temp < 0) return 0.5;
        }
        return 0.0;
    }

    /**
     * Adds additional fee based on temperature for bikes.
     * @param windSpeed - Current wind speed.
     * @param vehicleType - Vehicle type used for delivery.
     * @return - Returns additional wind speed fee
     * or throws exception if wind speed is unsafe for bike riding.
     */
    private double applyWindFee(double windSpeed, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Bike")) {
            if (windSpeed > 20) throw new RuntimeException("Usage of selected vehicle type is forbidden");
            if (windSpeed >= 10) return 0.5;
        }
        return 0.0;
    }

    /**
     * Adds additional fee based on weather phenomenon for scooters and bikes.
     * @param weatherPhenomenon - Current weather phenomenon.
     * @param vehicleType - Vehicle type used for delivery.
     * @return - Returns additional weather phenomenon fee
     * or throws exception if weather is unsafe for delivering in scooter or bike.
     */
    private double applyWeatherPhenomenonFee(String weatherPhenomenon, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Bike") || vehicleType.equalsIgnoreCase("Scooter")) {
            if (weatherPhenomenon.toLowerCase().contains("snow") || weatherPhenomenon.toLowerCase().contains("sleet")) return 1.0;
            if (weatherPhenomenon.toLowerCase().contains("rain")) return 0.5;
            if (weatherPhenomenon.toLowerCase().contains("storm")) throw new RuntimeException("Usage of selected vehicle type is forbidden");
        }
        return 0.0;
    }
}
