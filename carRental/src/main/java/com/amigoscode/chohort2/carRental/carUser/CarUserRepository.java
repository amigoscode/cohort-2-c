package com.amigoscode.chohort2.carRental.carUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarUserRepository extends JpaRepository<CarUser, Long> {
}
