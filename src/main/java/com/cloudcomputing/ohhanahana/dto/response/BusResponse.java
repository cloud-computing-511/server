package com.cloudcomputing.ohhanahana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusResponse {
    // 셔틀버스
    private BusStop shuttle;
    // 독정이고개
    private BusStop ddg;
    // 용현고가교
    private BusStop yg;
    // 인하대정문
    private BusStop inhaFrontGate;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusStop {
        private String busStopName;
        private String busStopNumber;
        private String busNumber;
        private int remainTime;
        private int remainBusStop;
        private int congestion;
        private String des;
        private int estimatedTime;
        private Boolean isTransfer;
    }
}
