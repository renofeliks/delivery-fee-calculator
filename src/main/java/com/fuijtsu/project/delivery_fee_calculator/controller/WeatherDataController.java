package com.fuijtsu.project.delivery_fee_calculator.controller;

import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherDataController {
    private final WeatherDataRepository repository;

    public WeatherDataController(WeatherDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<WeatherData> getAllWeatherData() {
        return repository.findAll();
    }

    @GetMapping("/{station}")
    public ResponseEntity<WeatherData> getLatestWeather(@PathVariable String station) {
        return repository.findTopByStationNameOrderByTimestampDesc(station)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
