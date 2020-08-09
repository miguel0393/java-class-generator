package com.example.java_class_generator;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MainApplication.class, args);
		Application.launch(FormApplication.class, args);
	}

	@Bean
	public GenerateClassController generateClassController(FileWriter fileWriter) {
		return new GenerateClassController(fileWriter);
	}
}
