package com.cloudcomputing.ohhanahana.mapper;

import com.cloudcomputing.ohhanahana.dto.response.BusResponse;
import com.cloudcomputing.ohhanahana.dto.response.RecommendResponse;
import com.cloudcomputing.ohhanahana.enums.Bus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BusMapper {

    public static BusResponse.BusStop toBusStop(RecommendResponse.Bus bus) {
        return new BusResponse.BusStop(
                bus.getBusStopName(),
                bus.getBusStopNumber(),
                bus.getBusNumber(),
                bus.getRemainTime(),
                bus.getRemainBusStop(),
                bus.getCongestion(),
                getBusDescription(bus.getBusNumber()),
                getBusEstimatedTime(bus.getBusNumber()),
                getBusTransfer(bus.getBusNumber())
        );
    }

    public static String getBusDescription(String busNumber) {
        return Arrays.stream(Bus.values())
                .filter(bus -> busNumber.equals(bus.getBusNumber()))
                .map(bus -> bus.getDes().getToKorean())
                .findFirst()
                .orElse("Error");
    }

    public static int getBusEstimatedTime(String busNumber) {
        return Arrays.stream(Bus.values())
                .filter(bus -> busNumber.equals(bus.getBusNumber()))
                .mapToInt(Bus::getTime)
                .findFirst()
                .orElse(0);
    }

    public static Boolean getBusTransfer(String busNumber) {
        return Arrays.stream(Bus.values())
                .filter(bus -> busNumber.equals(bus.getBusNumber()))
                .map(Bus::getIsTransfer)
                .findFirst()
                .orElse(null);
    }

    public static RecommendResponse validateBus(List<RecommendResponse.Bus> buses) {
        List<RecommendResponse.Bus> filteredBuses = buses.stream()
                .filter(bus -> Arrays.stream(Bus.values())
                        .anyMatch(enumBus -> enumBus.getBusRouteId().equals(bus.getBusId())
                                && enumBus.getSrc().getBusStopId().equals(bus.getBusStopId())))
                .peek(bus -> Arrays.stream(Bus.values())
                        .filter(enumBus -> enumBus.getBusRouteId().equals(bus.getBusId()))
                        .findFirst()
                        .ifPresent(enumBus -> bus.setBusNumber(enumBus.getBusNumber())))
                .toList();

        return new RecommendResponse(filteredBuses);
    }
}