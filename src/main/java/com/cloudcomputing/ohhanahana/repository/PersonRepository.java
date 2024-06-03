package com.cloudcomputing.ohhanahana.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.cloudcomputing.ohhanahana.entity.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Person save(Person person) {
        dynamoDBMapper.save(person);
        return person;
    }

    public Person findById(String id) {
        return dynamoDBMapper.load(Person.class, id);
    }

    public List<Person> findAll() {
        return dynamoDBMapper.scan(Person.class, new DynamoDBScanExpression());
    }

    public String update(String id, Person person) {
        dynamoDBMapper.save(person,
            new DynamoDBSaveExpression()
                .withExpectedEntry("id",
                    new ExpectedAttributeValue(
                        new AttributeValue().withS(id)
                    )));
        return id;
    }

    public String delete(String id) {
        dynamoDBMapper.delete(id);
        return "Person deleted successfully::" +id;
    }
}