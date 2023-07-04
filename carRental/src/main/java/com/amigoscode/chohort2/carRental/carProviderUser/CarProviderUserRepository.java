package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarProviderUserRepository extends CRJpaRepository<CarProviderUser,Long> {
    Optional<CarProviderUser> findByUserId(@Param("user_id") Long userId);
}
