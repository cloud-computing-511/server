package com.cloudcomputing.ohhanahana.controller;

import com.cloudcomputing.ohhanahana.dto.response.BusResponse;
import com.cloudcomputing.ohhanahana.dto.response.RecommendResponse;
import com.cloudcomputing.ohhanahana.dto.response.ShuttleResponse;
import com.cloudcomputing.ohhanahana.service.BusService;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bus")
public class BusController {

    private final BusService busService;

    @GetMapping("/recommend")
    public ResponseEntity<RecommendResponse> recommendBus() {
        try {
            RecommendResponse response = busService.getBusArrivalData(); // 인스턴스를 통해 메서드 호출
            return ResponseEntity.ok(response);
        } catch (JAXBException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/shuttle")
    public ResponseEntity<ShuttleResponse> shuttleBus() {
        return busService.getShuttleBus()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(null));
    }

    @GetMapping
    public ResponseEntity<BusResponse> findAllBus() {
        try {
            BusResponse response = busService.getAllBus(); // 인스턴스를 통해 메서드 호출
            return ResponseEntity.ok(response);
        } catch (JAXBException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

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
