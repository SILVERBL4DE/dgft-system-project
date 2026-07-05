package com.htc.dgft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DgftSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgftSystemApplication.class, args);
	}

}
