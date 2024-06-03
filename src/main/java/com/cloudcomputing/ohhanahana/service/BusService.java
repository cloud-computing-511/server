package com.cloudcomputing.ohhanahana.service;

import com.cloudcomputing.ohhanahana.dto.response.RecommendResponse;
import com.cloudcomputing.ohhanahana.dto.response.ServiceResult;
import com.cloudcomputing.ohhanahana.dto.response.ShuttleResponse;
import com.cloudcomputing.ohhanahana.enums.Bus;
import com.cloudcomputing.ohhanahana.enums.BusStop;
import com.cloudcomputing.ohhanahana.enums.ShuttleBus;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
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

    private final String baseUri = "http://apis.data.go.kr/6280000/busArrivalService/getAllRouteBusArrivalList?serviceKey=";
    private final String serviceKey = "";

    public RecommendResponse getBusArrivalData() throws JAXBException {
        List<BusStop> busStops = Arrays.stream(BusStop.values()).toList();

        RestTemplate restTemplate = createRestTemplate();
        List<RecommendResponse.Bus> buses = new ArrayList<>();

        for (BusStop busStop : busStops) {
            String apiUrl = baseUri + serviceKey
                    + "&numOfRows=10&pageNo=1&bstopId=" + busStop.getBusStopId();

            String response = restTemplate.getForObject(apiUrl, String.class);

            if (response != null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(ServiceResult.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                ServiceResult serviceResult = (ServiceResult) unmarshaller.unmarshal(new StringReader(response));

                if (serviceResult.getMsgBody() != null && serviceResult.getMsgBody().getItemList() != null) {
                    List<ServiceResult.MsgBody.Item> items = serviceResult.getMsgBody().getItemList();
                    for (ServiceResult.MsgBody.Item item : items) {
                        RecommendResponse.Bus bus = new RecommendResponse.Bus(
                                item.getRouteId(),
                                "",
                                item.getBstopId(),
                                busStop.getBusStopNumber(),
                                busStop.getBusStopName(),
                                item.getArrivalEstimateTime(),
                                item.getRestStopCount(),
                                item.getCongestion()
                        );
                        buses.add(bus);
                    }
                }
            }
        }

        return validateBus(buses);
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
                    LocalTime busTime = LocalTime.of(bus.getHour(), bus.getMinute());
                    return busTime.isAfter(now) && busTime.isBefore(oneHourLater);
                })
                .min(Comparator.comparing(bus -> LocalTime.of(bus.getHour(), bus.getMinute())));
    }


    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return factory;
    }

    private RecommendResponse validateBus(List<RecommendResponse.Bus> buses) {

        List<RecommendResponse.Bus> filteredBuses = buses.stream()
                .filter(bus -> {
                    for (Bus enumBus : Bus.values()) {
                        if (enumBus.getBusRouteId().equals(bus.getBusId())) {
                            bus.setBusNumber(enumBus.getBusNumber());
                            return true;
                        }
                    }
                    return false;
                })
                .toList();

        return new RecommendResponse(filteredBuses);
    }
}