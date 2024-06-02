package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bus {
    B_511("165000073", "511"),
    B_13("165000016", "13"),
    B_1601("165000154", "1601"),
    B_38("165000006", "38"),
    B_5_1("165000006", "5-1"),
    B_512("165000074", "512"),
    B_516("165000078", "516"),
    B_519("165000081", "519"),
    B_8("165000012", "8"),
    B_801("169000027", "801"),
    B_8A("165000364", "8A"),
    B_31("168000040", "인천e음31")
    ;
    private final String busRouteId;
    private final String busNumber;

}
