package com.cloudcomputing.ohhanahana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OhhanahanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OhhanahanaApplication.class, args);
	}

}
