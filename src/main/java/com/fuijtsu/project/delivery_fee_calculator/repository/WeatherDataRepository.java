package com.fuijtsu.project.delivery_fee_calculator.repository;


import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing WeatherData entities from the database.
 */
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Finds the most recent weather data for a give weather station.
     * Sorts all matching records by timestamp in descending order and returns the first one.
     * Uses derived query, so I don't need to write SQL myself.
     * @param stationName - Name of the weather station.
     * @return - Optional containing the latest weather data or empty if not found.
     */
    Optional<WeatherData> findTopByStationNameOrderByTimestampDesc(String stationName);
}
