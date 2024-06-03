package com.cloudcomputing.ohhanahana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleResponse {
    private int remainTime;
    private LocalTime departureTime;
    private String src;
    private String des;
}
