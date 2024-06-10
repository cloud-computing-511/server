package com.cloudcomputing.ohhanahana.service;

import com.cloudcomputing.ohhanahana.dto.response.SensorDataResponse;
import com.cloudcomputing.ohhanahana.enums.Congestion;
import com.cloudcomputing.ohhanahana.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public SensorDataResponse findSensorData() {
        sensorDataRepository.findLatest();

        String congestion = Congestion.NORMAL.getToKorean();

        return SensorDataResponse.toDTO("인하대학교",
            "2024-05-31 16:30 PM",
            congestion,
            25,
            20);
    }
}
