package com.stockify.stockifyapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.repository.UserRepository;

@SpringBootApplication
public class StockifyAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockifyAppApplication.class, args);
	}

	// // add some basic data to the database
	// @Bean
	// CommandLineRunner initDatabase(UserRepository userRepository) {

	// return args -> {
	// userRepository.save(new User("John", "+34666666666", "carlos@gmail.com"));
	// };
	// }

}