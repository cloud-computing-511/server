package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Congestion {

    SPARE(1, "여유"),
    NORMAL(2, "일반"),
    CONGESTION(3, "혼잡"),
    EXTRA(255, "모름")

    ;
    private final int number;
    private final String toKorean;
}
