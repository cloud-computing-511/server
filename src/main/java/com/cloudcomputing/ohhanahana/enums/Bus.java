package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bus {
    B_511("165000073", "511", BusStop.YONGHYUN, Destination.JUAN, 20, false),
    // B_27("", "27", BusStop.YONGHYUN, Destination.JUAN, 25, true),

    B_13("165000016", "13", BusStop.DGG_1, Destination.JUAN, 22, false),
    B_38("165000006", "38", BusStop.DGG_1, Destination.JUAN, 16, false),
    // B_28_1("", "28-1", BusStop.DGG_2, Destination.JUAN, 15, false),

    B_5_1("165000006", "5-1", BusStop.INHA_FRONT_GATE_1, Destination.JUAN, 25, false),
    B_516("165000078", "516", BusStop.INHA_FRONT_GATE_1, Destination.JUAN, 30, false),
    B_8("165000012", "8", BusStop.INHA_FRONT_GATE_1, Destination.SIMINGONGONE, 20, false),
    B_8A("165000364", "8A", BusStop.INHA_FRONT_GATE_1, Destination.SIMINGONGONE, 20, false),

    // B_517("", "517", BusStop.INHA_FRONT_GATE_2, Destination.JEMULPO, 20, false),
    B_519("165000081", "519", BusStop.INHA_FRONT_GATE_2, Destination.JEMULPO, 20, false),
    ;
    private final String busRouteId;
    private final String busNumber;
    private final BusStop src;
    private final Destination des;
    private final int time;
    // 환승 여부
    private final Boolean isTransfer;

}
