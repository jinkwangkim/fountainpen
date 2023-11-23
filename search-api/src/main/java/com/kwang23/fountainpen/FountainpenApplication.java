package com.kwang23.fountainpen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FountainpenApplication {

	public static void main(String[] args) {
		SpringApplication.run(FountainpenApplication.class, args);
	}

}
