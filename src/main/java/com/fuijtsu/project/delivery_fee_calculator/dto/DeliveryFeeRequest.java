package com.fuijtsu.project.delivery_fee_calculator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) used for requesting delivery fee calculation.
 * I use DTO in this context, because it helps validate and structure input
 * before data is processed by DeliveryFeeService.
 * Validation constraints ensure the values are valid and not empty.
 */
public class DeliveryFeeRequest {

    @NotBlank(message = "City can't be empty")
    @Pattern(regexp = "Tallinn|Tartu|Pärnu", message = "Invalid city name")
    private String city;

    @NotBlank(message = "Vehicle type must not be empty")
    @Pattern(regexp = "Car|Scooter|Bike", message = "Invalid vehicle type")
    private String vehicleType;

    public DeliveryFeeRequest() {}

    public DeliveryFeeRequest(String city, String vehicleType) {
        this.city = city;
        this.vehicleType = vehicleType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
