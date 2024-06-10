package com.cloudcomputing.ohhanahana.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Destination {
    JUAN("주안역"),
    JEMULPO("제물포역"),
    SIMINGONGONE("시민공원역")
    ;
    private final String toKorean;
}
