package com.example.newdemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewdemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewdemoApplication.class, args);
		System.out.println(" Your Project is Successfully Started");
	}
	// it reduce the code
	// modelmapper-https://www.geeksforgeeks.org/spring-boot-map-entity-to-dto-using-modelmapper/
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
