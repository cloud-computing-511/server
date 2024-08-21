package com.cloudcomputing.ohhanahana.controller;

import com.cloudcomputing.ohhanahana.dto.response.SensorDataResponse;
import com.cloudcomputing.ohhanahana.service.SensorDataService;
import jakarta.xml.bind.JAXBException;
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

    @GetMapping("/")
    public ResponseEntity<Object> findTest() throws JAXBException {
        return ResponseEntity.ok(sensorDataService.findLatestSensorData());
    }

    @GetMapping("/random")
    public ResponseEntity<Object> findRandomSensorData() throws JAXBException {
        return ResponseEntity.ok(sensorDataService.generateRandomSensorData());
    }
}