package com.cloudcomputing.ohhanahana.service;

import com.cloudcomputing.ohhanahana.config.RestTemplateConfig;
import com.cloudcomputing.ohhanahana.dto.response.BusResponse;
import com.cloudcomputing.ohhanahana.dto.response.RecommendResponse;
import com.cloudcomputing.ohhanahana.dto.response.ServiceResult;
import com.cloudcomputing.ohhanahana.dto.response.ShuttleResponse;
import com.cloudcomputing.ohhanahana.enums.Bus;
import com.cloudcomputing.ohhanahana.enums.BusStop;
import com.cloudcomputing.ohhanahana.enums.ShuttleBus;
import com.cloudcomputing.ohhanahana.mapper.BusMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BusService {

    private final String BASE_URI = "http://apis.data.go.kr/6280000/busArrivalService/getAllRouteBusArrivalList?serviceKey=";

    @Value("${api.bus-information.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    public BusResponse getAllBus() throws JAXBException {
        Optional<ShuttleBus> shuttleBus = findShuttleBus();
        RecommendResponse busArrivalData = getBusArrivalData();

        BusResponse.BusStop inhaFrontGateBusStop = getBusStopInfo(busArrivalData, "인하대학교 정문", BusStop.INHA_FRONT_GATE_1, BusStop.INHA_FRONT_GATE_2);
        BusResponse.BusStop ddgBusStop = getBusStopInfo(busArrivalData,  "독정이고개", BusStop.DGG_1, BusStop.DGG_2);
        BusResponse.BusStop ygBusStop = getBusStopInfo(busArrivalData, "용현고가교", BusStop.YONGHYUN);

        BusResponse.BusStop shuttleBusStop = getShuttleBusStop(shuttleBus);

        return new BusResponse(shuttleBusStop, ddgBusStop, ygBusStop, inhaFrontGateBusStop);
    }

    public RecommendResponse.Bus get511Bus() throws JAXBException {
        String apiUrl = buildApiUrl(BusStop.INHA_BACK_GATE.getBusStopId());
        String response = restTemplate.getForObject(apiUrl, String.class);

        if (response != null) {
            return parseBusArrivalData(response, BusStop.INHA_BACK_GATE).stream()
                    .findFirst()
                    .map(bus -> {
                        bus.setBusNumber("511");
                        return bus;
                    })
                    .orElse(null);
        }
        return null;
    }

    private BusResponse.BusStop getShuttleBusStop(Optional<ShuttleBus> shuttleBus) {
        ShuttleBus bus = ShuttleBus.SHUTTLE_NONE;
        int secondsUntilBus = -1;
        if (shuttleBus.isPresent()) {
            bus = shuttleBus.get();
            secondsUntilBus = (int) Duration.between(LocalTime.now(),
                    LocalTime.of(bus.getHour(), bus.getMinute())).getSeconds();
        }

        return new BusResponse.BusStop(bus.getSrc(), null, "셔틀버스",
                secondsUntilBus, 0, 0, bus.getDes(), 20, false);

    }

    public RecommendResponse getBusArrivalData() throws JAXBException {
        List<RecommendResponse.Bus> buses = new ArrayList<>();

        for (BusStop busStop : BusStop.values()) {
            String apiUrl = buildApiUrl(busStop.getBusStopId());
            String response = restTemplate.getForObject(apiUrl, String.class);

            if (response != null) {
                buses.addAll(parseBusArrivalData(response, busStop));
            }
        }

        return BusMapper.validateBus(buses);
    }

    private String buildApiUrl(String busStopId) {
        return BASE_URI + serviceKey + "&numOfRows=10&pageNo=1&bstopId=" + busStopId;
    }

    private List<RecommendResponse.Bus> parseBusArrivalData(String response, BusStop busStop) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ServiceResult.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ServiceResult serviceResult = (ServiceResult) unmarshaller.unmarshal(new StringReader(response));

        if (serviceResult.getMsgBody() == null || serviceResult.getMsgBody().getItemList() == null) return Collections.emptyList();

        List<RecommendResponse.Bus> buses = new ArrayList<>();
        for (ServiceResult.MsgBody.Item item : serviceResult.getMsgBody().getItemList()) {
            buses.add(new RecommendResponse.Bus(
                    item.getRouteId(),
                    "",
                    item.getBstopId(),
                    busStop.getBusStopNumber(),
                    busStop.getBusStopName(),
                    item.getArrivalEstimateTime(),
                    item.getRestStopCount(),
                    item.getCongestion()
            ));
        }
        return buses;
    }

    private BusResponse.BusStop getBusStopInfo(RecommendResponse busArrivalData, String busStopName, BusStop... busStops) {
        return busArrivalData.getBuses().stream()
                .filter(bus -> Arrays.stream(busStops).anyMatch(stop -> stop.getBusStopId().equals(bus.getBusStopId())))
                .min(Comparator.comparingInt(RecommendResponse.Bus::getRemainTime))
                .map(BusMapper::toBusStop)
                .orElse(
                        new BusResponse.BusStop(busStopName, "없음", "없음", -1,
                                -1, -1, "없음", -1, false)
                );
    }

    public Optional<ShuttleResponse> getShuttleBus() {
        return findShuttleBus().map(bus -> {
            LocalTime now = LocalTime.now();
            LocalTime busTime = LocalTime.of(bus.getHour(), bus.getMinute());
            int secondsUntilBus = (int) Duration.between(now, busTime).getSeconds();
            return new ShuttleResponse(
                    secondsUntilBus,
                    busTime,
                    bus.getSrc(),
                    bus.getDes()
            );
        });
    }

    private Optional<ShuttleBus> findShuttleBus() {
        LocalTime now = LocalTime.now();
        LocalTime oneHourLater = now.plusHours(1);

        return Arrays.stream(ShuttleBus.values())
                .filter(bus -> {
                    if (!bus.equals(ShuttleBus.SHUTTLE_NONE)) {
                        LocalTime busTime = LocalTime.of(bus.getHour(), bus.getMinute());
                        return busTime.isAfter(now) && busTime.isBefore(oneHourLater);
                    }
                    return false;
                })
                .min(Comparator.comparing(bus -> LocalTime.of(bus.getHour(), bus.getMinute())));
    }
}