package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusStop {
    INHA_BACK_GATE("163000165", "37165", "인하대후문"),
    INHA_FRONT_GATE("163000099", "37099", "인하대정문"),
    INHA_SUBWAY_4TH("163000122", "37122", "인하대역4번출구"),
    INHA_SUBWAY_7TH("163000091", "37091", "인하대역7번출구"),
    DGG("163000234", "37234", "독정이고개"),
    YONGHYUN1("163000611", "37611", "용현고가교"),
    YONGHYUN2("163000550", "37550", "용현고가교")

    ;

    private final String busStopId;
    private final String busStopNumber;
    private final String busStopName;


}
