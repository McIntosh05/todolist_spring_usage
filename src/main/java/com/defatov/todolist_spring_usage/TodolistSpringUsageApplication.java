package com.defatov.todolist_spring_usage;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableEncryptableProperties
@EnableMongoRepositories
public class TodolistSpringUsageApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistSpringUsageApplication.class, args);
	}

}
