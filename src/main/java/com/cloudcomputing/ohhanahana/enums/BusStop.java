package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusStop {
    // INHA_BACK_GATE("163000165", "37165", "인하대후문"),
    INHA_FRONT_GATE_1("163000099", "37099", "인하대정문(1생활관 앞)"),
    INHA_FRONT_GATE_2("", "37104", "인하대정문(1생활관 건너편)"),
    DGG_1("163000234", "37234", "독정이고개"),
    DGG_2("", "37238", "독정이고개"),
    YONGHYUN("163000611", "37611", "용현고가교"),
    ;

    private final String busStopId;
    private final String busStopNumber;
    private final String busStopName;


}
