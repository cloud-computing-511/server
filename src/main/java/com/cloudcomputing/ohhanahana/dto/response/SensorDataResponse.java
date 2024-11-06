package com.cloudcomputing.ohhanahana.dto.response;

import com.cloudcomputing.ohhanahana.enums.Congestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SensorDataResponse {

    private String currentLocation;
    private String currentDateTime;
    private Congestion congestion;
    private int expectedWaitingTime;
    private String expectedWaitingPeople;

    public static SensorDataResponse toDTO(String currentLocation, String currentDateTime,
        Congestion congestion,
        int expectedWaitingTime, String expectedWaitingPeople) {

        return SensorDataResponse.builder()
            .currentLocation(currentLocation)
            .currentDateTime(currentDateTime)
            .congestion(congestion)
            .expectedWaitingTime(expectedWaitingTime)
            .expectedWaitingPeople(expectedWaitingPeople)
            .build();
    }
}