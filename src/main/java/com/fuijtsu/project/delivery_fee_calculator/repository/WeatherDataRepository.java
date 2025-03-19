package com.fuijtsu.project.delivery_fee_calculator.repository;


import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    // Query to find the latest weather data for a city by station name ordered by timestamp desc.
    Optional<WeatherData> findTopByStationNameOrderByTimestampDesc(String stationName);
}
