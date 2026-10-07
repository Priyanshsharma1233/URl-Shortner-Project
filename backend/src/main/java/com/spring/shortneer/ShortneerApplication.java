package com.spring.shortneer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShortneerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortneerApplication.class, args);
	}

}