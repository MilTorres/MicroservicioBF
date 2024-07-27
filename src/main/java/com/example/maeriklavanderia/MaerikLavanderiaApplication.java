package com.example.maeriklavanderia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MaerikLavanderiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaerikLavanderiaApplication.class, args);
	}

}
