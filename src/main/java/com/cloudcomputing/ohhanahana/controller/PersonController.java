package com.cloudcomputing.ohhanahana.controller;

import com.cloudcomputing.ohhanahana.entity.Person;
import com.cloudcomputing.ohhanahana.repository.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Person API", description = "Person 관련 API")
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Operation(summary = "Person 저장 API", description = "Person을 저장하는 API입니다.")
    @PostMapping
    public Person save(@RequestBody Person person) {

        return personRepository.save(person);
    }

    @Operation(summary = "Person 조회 API", description = "Person을 조회하는 API입니다.")
    @GetMapping("/{id}")
    public Person findById(@PathVariable(value = "id") String id) {
        return personRepository.findById(id);
    }

    @Operation(summary = "Person 전체 조회 API", description = "Person 전체를 조회하는 API입니다.")
    @GetMapping("/all")
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Operation(summary = "Person 수정 API", description = "Person을 수정하는 API입니다.")
    @PutMapping("/{id}")
    public String update(@PathVariable(value="id") String id,
    @RequestBody Person person) {
        return personRepository.update(id, person);
    }

    @Operation(summary = "Person 삭제 API", description = "Person을 삭제하는 API입니다.")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value="id") String id) {
        return personRepository.delete(id);
    }
}
