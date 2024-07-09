package com.young.in.youngk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class YoungkApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoungkApplication.class, args);
	}

}
