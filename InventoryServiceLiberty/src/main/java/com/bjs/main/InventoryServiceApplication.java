package com.bjs.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = { "com.bjs" })
@EnableMongoRepositories({ "com.bjs.repository" })
@EnableEurekaClient
public class InventoryServiceApplication extends SpringBootServletInitializer {

	/*
	 * public static void main(String[] args) {
	 * SpringApplication.run(InventoryServiceApplication.class, args); }
	 */

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InventoryServiceApplication.class);
	}
}

@RestController
class Test {

	@RequestMapping("/hello")
	public String getMessage() {

		return "working";
	}

}
