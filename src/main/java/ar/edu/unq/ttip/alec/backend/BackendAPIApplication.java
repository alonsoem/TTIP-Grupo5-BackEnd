package ar.edu.unq.desapp.grupoj.backenddesappapi;

import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.FrontUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@EnableJpaRepositories(basePackageClasses = FrontUserRepository.class)
@SpringBootApplication
public class BackendDesappApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendDesappApiApplication.class, args);
	}

}
