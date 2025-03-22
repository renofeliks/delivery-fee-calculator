package com.fuijtsu.project.delivery_fee_calculator.controller;

import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Provides endpoints for retrieving weather data.
 * Offers functionality to get all records or the latest data for a specific station.
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherDataController {
    private final WeatherDataRepository repository;

    public WeatherDataController(WeatherDataRepository repository) {
        this.repository = repository;
    }

    /**
     * GET endpoint to retrieve all stored weather data entries.
     * @return - A list of all WeatherData objects in the database.
     */
    @GetMapping("/all")
    public List<WeatherData> getAllWeatherData() {
        return repository.findAll();
    }

    /**
     * GET endpoint to retrieve the latest weather data for a given station.
     * @param station - Name of the station for which we want weather data.
     * @return - ResponseEntity containing the latest weather data.
     */
    @GetMapping("/{station}")
    public ResponseEntity<WeatherData> getLatestWeather(@PathVariable String station) {
        return repository.findTopByStationNameOrderByTimestampDesc(station)
                .map(ResponseEntity::ok) // Returns 200 if OK.
                .orElse(ResponseEntity.notFound().build()); // Returns 404 Not Found.
    }
}
