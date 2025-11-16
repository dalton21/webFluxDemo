package com.hastalasnubes.webfluxDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.hastalasnubes")
@EnableReactiveMongoRepositories(basePackages = "com.hastalasnubes.repository")
public class WebfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoApplication.class, args);
	}

}
