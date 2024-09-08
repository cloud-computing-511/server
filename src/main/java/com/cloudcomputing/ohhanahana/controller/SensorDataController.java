package com.cloudcomputing.ohhanahana.controller;

import com.cloudcomputing.ohhanahana.dto.response.SensorDataResponse;
import com.cloudcomputing.ohhanahana.service.SensorDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "혼잡도 API", description = "혼잡도 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/congestion")
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @Operation(summary = "혼잡도 조회 API", description = "혼잡도를 조회하는 API입니다.")
    @GetMapping("/")
    public ResponseEntity<Object> findTest() throws JAXBException {
        return ResponseEntity.ok(sensorDataService.findLatestSensorData());
    }

    @Operation(summary = "혼잡도 랜덤 조회 API", description = "혼잡도를 랜덤으로 조회하는 API입니다.")
    @GetMapping("/random")
    public ResponseEntity<Object> findRandomSensorData() throws JAXBException {
        return ResponseEntity.ok(sensorDataService.generateRandomSensorData());
    }
}