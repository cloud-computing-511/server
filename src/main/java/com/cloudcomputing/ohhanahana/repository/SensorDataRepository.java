package com.cloudcomputing.ohhanahana.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SensorDataRepository {

    public void findLatest() {
        //TODO - 다이나모 디비에서 최신값 불러오기
    }
}