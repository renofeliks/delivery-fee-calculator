package com.fuijtsu.project.delivery_fee_calculator.controller;

import com.fuijtsu.project.delivery_fee_calculator.dto.DeliveryFeeRequest;
import com.fuijtsu.project.delivery_fee_calculator.service.DeliveryFeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles requests to calculate delivery fees
 * based on city and vehicle type.
 * Receives data from the client and returns the calculated fee.
 */
@RestController
@RequestMapping("/api/delivery-fee")
public class DeliveryFeeController {
    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(DeliveryFeeService service) {
        this.deliveryFeeService = service;
    }

    /**
     * POST endpoint for calculating delivery fee.
     * Validates the incoming request and returns the computed fee.
     * @param request
     * @return
     */
    @PostMapping("/calculate")
    public ResponseEntity<Double> calcFee(@Valid @RequestBody DeliveryFeeRequest request) {
        double fee = deliveryFeeService.calcDeliveryFee(request);
        return ResponseEntity.ok(fee);
    }
}
