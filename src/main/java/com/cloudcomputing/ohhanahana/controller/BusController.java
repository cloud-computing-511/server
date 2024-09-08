package com.cloudcomputing.ohhanahana.controller;

import com.cloudcomputing.ohhanahana.dto.response.BusResponse;
import com.cloudcomputing.ohhanahana.dto.response.RecommendResponse;
import com.cloudcomputing.ohhanahana.dto.response.ShuttleResponse;
import com.cloudcomputing.ohhanahana.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "버스 API", description = "버스 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bus")
public class BusController {

    private final BusService busService;

    @Operation(summary = "주변 정류장 버스 도착 정보 조회 API", description = "주변 정류장 버스 도착 정보를 조회하는 API입니다.")
    @GetMapping("/recommend")
    public ResponseEntity<RecommendResponse> recommendBus() {
        try {
            RecommendResponse response = busService.getBusArrivalData(); // 인스턴스를 통해 메서드 호출
            return ResponseEntity.ok(response);
        } catch (JAXBException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "셔틀버스 도착 정보 조회 API", description = "셔틀버스 도착 정보를 조회하는 API입니다.")
    @GetMapping("/shuttle")
    public ResponseEntity<ShuttleResponse> shuttleBus() {
        return busService.getShuttleBus()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(null));
    }

    @Operation(summary = "전체 버스 도착 정보 조회 API", description = "전체 버스 도착 정보를 조회하는 API입니다.")
    @GetMapping
    public ResponseEntity<BusResponse> findAllBus() {
        try {
            BusResponse response = busService.getAllBus(); // 인스턴스를 통해 메서드 호출
            return ResponseEntity.ok(response);
        } catch (JAXBException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "511번 버스 도착 정보 조회 API", description = "511번 버스 도착 정보를 조회하는 API입니다.")
    @GetMapping("/511")
    public ResponseEntity<RecommendResponse.Bus> get511Bus() {
        try {
            RecommendResponse.Bus response = busService.get511Bus();
            return ResponseEntity.ok(response);
        } catch (JAXBException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
