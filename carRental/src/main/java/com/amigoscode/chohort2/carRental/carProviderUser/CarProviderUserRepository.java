package com.amigoscode.chohort2.carRental.carProviderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarProviderUserRepository extends JpaRepository<CarProviderUser,Long> {
    Optional<CarProviderUser> findByUserId(Long userId);
}
