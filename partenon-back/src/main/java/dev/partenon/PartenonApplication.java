package dev.partenon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class PartenonApplication {
	public static void main(String[] args) {
		SpringApplication.run(PartenonApplication.class, args);
	}
}
