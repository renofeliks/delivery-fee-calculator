package com.fuijtsu.project.delivery_fee_calculator.service;

import com.fuijtsu.project.delivery_fee_calculator.dto.DeliveryFeeRequest;
import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DeliveryFeeServiceTest {
    private DeliveryFeeService deliveryFeeService;
    private WeatherDataRepository weatherDataRepository;

    @BeforeEach
    void setUp() {
        weatherDataRepository = Mockito.mock(WeatherDataRepository.class);
        deliveryFeeService = new DeliveryFeeService(weatherDataRepository);
    }

    @Test
    void shouldCalcBaseFeeForTallinnBike() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "Bike");
        WeatherData mockWeather = new WeatherData("Tallinn-Harku", "26038", 5.0, 5.0, "Clear", null);

        when(weatherDataRepository.findTopByStationNameOrderByTimestampDesc("Tallinn-Harku")).thenReturn(Optional.of(mockWeather));

        double fee = deliveryFeeService.calcDeliveryFee(request);
        assertEquals(3.0, fee);
    }

    @Test
    void shouldApplyColdWeatherFeeForScooter() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tartu", "Scooter");
        WeatherData mockWeather = new WeatherData("Tartu-Tõravere", "26242", -15.0, 3.0, "Clear", null);

        when(weatherDataRepository.findTopByStationNameOrderByTimestampDesc("Tartu-Tõravere")).thenReturn(Optional.of(mockWeather));

        double fee = deliveryFeeService.calcDeliveryFee(request);
        assertEquals(4.0, fee);
    }

    @Test
    void shouldApplyWindFeeForBike() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Pärnu", "Bike");
        WeatherData mockWeather = new WeatherData("Pärnu", "41803", 10.0, 12.0, "Clear", null);

        when(weatherDataRepository.findTopByStationNameOrderByTimestampDesc("Pärnu")).thenReturn(Optional.of(mockWeather));

        double fee = deliveryFeeService.calcDeliveryFee(request);
        assertEquals(3.0, fee);
    }

    @Test
    void shouldThrowExceptionForExtremeWind() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "Bike");
        WeatherData mockWeather = new WeatherData("Tallinn-Harku", "26038", 5.0, 25.0, "Clear", null);

        when(weatherDataRepository.findTopByStationNameOrderByTimestampDesc("Tallinn-Harku")).thenReturn(Optional.of(mockWeather));

        Exception exception = assertThrows(RuntimeException.class, () -> deliveryFeeService.calcDeliveryFee(request));
        assertEquals("Usage of selected vehicle type is forbidden", exception.getMessage());
    }
}
