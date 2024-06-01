package com.cloudcomputing.ohhanahana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResponse {
    private List<Bus> buses;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bus {
        private String busId;
        private String busNumber;
        private String busStopId;
        private String busStopNumber;
        private String busStopName;
        private int remainTime;
        private int remainBusStop;
        private int congestion;


        public void setBusNumber(String busNumber) {
            this.busNumber = busNumber;
        }
    }
}
