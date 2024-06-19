package com.cloudcomputing.ohhanahana.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.cloudcomputing.ohhanahana.dto.response.SensorDataResponse;
import com.cloudcomputing.ohhanahana.entity.SensorData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SensorDataRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public List<SensorData> findAll() {
        return dynamoDBMapper.scan(SensorData.class, new DynamoDBScanExpression());
    }

    public SensorData findLatestByDeviceId(String deviceId) {
        DynamoDBQueryExpression<SensorData> queryExpression = new DynamoDBQueryExpression<SensorData>()
            .withHashKeyValues(SensorData.builder().deviceId(deviceId).build())
            .withScanIndexForward(true) //오름차순
            .withLimit(50); //가장 최신값 하나만 가져오기

        List<SensorData> result = dynamoDBMapper.query(SensorData.class, queryExpression);

        if(result.isEmpty()) {
            return null;
        }
        else {
            return result.get(0);
        }
    }
}