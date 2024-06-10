package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShuttleBus {
    SHUTTLE_BUS_1("교내 셔틀 승강장(정석 뒤편)", "주안역", 16, 0),
    SHUTTLE_BUS_2("교내 셔틀 승강장(정석 뒤편)", "주안역", 16, 30),
    SHUTTLE_BUS_3("교내 셔틀 승강장(정석 뒤편)", "주안역", 17, 0),
    SHUTTLE_BUS_4("교내 셔틀 승강장(정석 뒤편)", "주안역", 17, 30),
    SHUTTLE_NONE("교내 셔틀 승강장(정석 뒤편)", "주안역", -1,-1),
    ;

    private final String src;
    private final String des;
    private final int hour;
    private final int minute;
}
