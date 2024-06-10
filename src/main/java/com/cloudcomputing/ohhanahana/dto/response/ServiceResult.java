package com.cloudcomputing.ohhanahana.dto.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "ServiceResult")
@Setter
public class ServiceResult {
    private MsgBody msgBody;

    @XmlElement(name = "msgBody")
    public MsgBody getMsgBody() {
        return msgBody;
    }

    @Setter
    public static class MsgBody {
        private List<Item> itemList;

        @XmlElement(name = "itemList")
        public List<Item> getItemList() {
            return itemList;
        }

        @Setter
        public static class Item {
            private int arrivalEstimateTime;
            private String bstopId;
            private int congestion;
            private int restStopCount;
            private String routeId;

            @XmlElement(name = "ARRIVALESTIMATETIME")
            public int getArrivalEstimateTime() {
                return arrivalEstimateTime;
            }

            @XmlElement(name = "BSTOPID")
            public String getBstopId() {
                return bstopId;
            }

            @XmlElement(name = "CONGESTION")
            public int getCongestion() {
                return congestion;
            }

            @XmlElement(name = "REST_STOP_COUNT")
            public int getRestStopCount() {
                return restStopCount;
            }

            @XmlElement(name = "ROUTEID")
            public String getRouteId() {
                return routeId;
            }
        }
    }
}