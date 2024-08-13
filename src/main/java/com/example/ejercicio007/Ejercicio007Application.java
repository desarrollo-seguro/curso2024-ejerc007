package com.example.ejercicio007;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class Ejercicio007Application {

	public static void main(String[] args) {
		SpringApplication.run(Ejercicio007Application.class, args);
	}

}
