package com.amigoscode.chohort2.carRental;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepositoryImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CRJpaRepositoryImp.class)
public class CarRentalApplication {

	public static void main(String[] args) {
		System.out.println("Hello")
		SpringApplication.run(CarRentalApplication.class, args);
	}

}
