package com.cloudcomputing.ohhanahana.controller;

import com.cloudcomputing.ohhanahana.dto.response.SensorDataResponse;
import com.cloudcomputing.ohhanahana.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/congestion")
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @GetMapping
    public ResponseEntity<SensorDataResponse> findSensorData() {
        return ResponseEntity.ok(sensorDataService.findSensorData());
    }
}