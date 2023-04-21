package com.stockify.stockifyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StockifyAppApplication {
	public static void main(String[] args) {		
		SpringApplication.run(StockifyAppApplication.class, args);
	}

	// // // add some basic data to the database
	// @Bean
	// CommandLineRunner initDatabase(UserRepository userRepository) {

	// return args -> {
	// 	SuscriptionPlanServiceInitialData suscriptionPlanServiceInitialData = new SuscriptionPlanServiceInitialData();
	// 	suscriptionPlanServiceInitialData.addBulkData();

	// };
	// }

}