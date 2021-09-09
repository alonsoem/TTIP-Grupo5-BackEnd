package ar.edu.unq.ttip.alec.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ar.edu.unq.ttip.alec.backend.repository.FrontUserRepository;

@EnableCaching
@EnableJpaRepositories(basePackageClasses = FrontUserRepository.class)
@SpringBootApplication
public class BackendAPIApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendAPIApplication.class, args);
	}

}
