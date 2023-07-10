package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import com.amigoscode.chohort2.carRental.car.Car;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarProviderUserRepository extends CRJpaRepository<CarProviderUser,Long> {
    Optional<CarProviderUser> findCarProviderUserByUserId(Long id);
}
