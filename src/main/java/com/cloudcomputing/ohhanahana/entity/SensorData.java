package com.cloudcomputing.ohhanahana.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@DynamoDBTable(tableName = "ohhanahana-dynamodb")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorData {
    @DynamoDBHashKey(attributeName = "DeviceID")
    private String deviceId;

    @DynamoDBRangeKey(attributeName = "Timestamp")
    private Long timestamp;

    @DynamoDBAttribute(attributeName = "payload")
    private Map<String, AttributeValue> payload;
}
