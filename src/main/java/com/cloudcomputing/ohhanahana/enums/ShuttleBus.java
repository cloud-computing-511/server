package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShuttleBus {
    SHUTTLE_BUS_1("용현캠퍼스", "주안역", 16, 0),
    SHUTTLE_BUS_2("용현캠퍼스", "주안역", 16, 30),
    SHUTTLE_BUS_3("용현캠퍼스", "주안역", 17, 0),
    SHUTTLE_BUS_4("용현캠퍼스", "주안역", 17, 30),
    ;

    private final String src;
    private final String des;
    private final int hour;
    private final int minute;
}
