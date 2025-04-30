package com.noair.subsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.noair")
public class EasipApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasipApplication.class, args);
	}

}
