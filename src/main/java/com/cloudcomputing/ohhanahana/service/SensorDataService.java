package com.cloudcomputing.ohhanahana.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.cloudcomputing.ohhanahana.dto.response.SensorDataResponse;
import com.cloudcomputing.ohhanahana.entity.SensorData;
import com.cloudcomputing.ohhanahana.enums.Congestion;
import com.cloudcomputing.ohhanahana.repository.SensorDataRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public SensorDataResponse findSensorData() {
        //sensorDataRepository.findLatest();

        return null;
    }

    public SensorDataResponse findLatestSensorData() {
        Congestion congestion = null;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        //시간 체크(10 : 00 ~ 20 : 00)
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(10,0);
        LocalTime endTime = LocalTime.of(22,0);

        if(currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
            congestion = Congestion.SENSOR_INACTIVE;
        }

        else {
            SensorData sensorData1 = sensorDataRepository.findLatestByDeviceId("dev_1");
            SensorData sensorData2 = sensorDataRepository.findLatestByDeviceId("dev_2");

            if(sensorData1 != null && sensorData2 != null) {
                Map<String, AttributeValue> payload1 = sensorData1.getPayload();
                Map<String, AttributeValue> payload2 = sensorData2.getPayload();

                int sensor1Value = Integer.parseInt(payload1.get("Sensor1").getN());
                int sensor2Value = Integer.parseInt(payload1.get("Sensor2").getN());
                int sensor3Value = Integer.parseInt(payload2.get("Sensor3").getN());
                int sensor4Value = Integer.parseInt(payload2.get("Sensor4").getN());

                congestion = determineCongestion(sensor1Value, sensor2Value, sensor3Value, sensor4Value);

            }
            else {
                congestion = Congestion.SENSOR_ERROR;
            }
        }

        return SensorDataResponse.toDTO(
            "인하대학교",
            now.format(formatter),
            congestion,
            calculateWaitingTime(congestion),
            calculatePeople(congestion)
        );
    }

    private Congestion determineCongestion(int sensor1Value, int sensor2Value, int sensor3Value, int sensor4Value) {
        if (isWithinRange(sensor1Value) && isWithinRange(sensor2Value)) {
            if (isWithinRange(sensor3Value) && isWithinRange(sensor4Value)) {
                return Congestion.CONGESTION;
            } else {
                return Congestion.NORMAL;
            }
        } else {
            return Congestion.SPARE;
        }
    }

    private String calculateWaitingTime(Congestion congestion) {
        return switch (congestion) {
            case SPARE -> "10분";
            case NORMAL -> "15분";
            case CONGESTION -> "20분";
            default -> "-";
        };
    }

    private String calculatePeople(Congestion congestion) {
        return switch (congestion) {
            case SPARE -> "15명";
            case NORMAL -> "25명";
            case CONGESTION -> "35명";
            default -> "-";
        };
    }

    private boolean isWithinRange(int value) {
        int SENSOR_MAX_VALUE = 150;
        int SENSOR_MIN_VALUE = 1;
        return value >= SENSOR_MIN_VALUE && value <= SENSOR_MAX_VALUE;
    }
}