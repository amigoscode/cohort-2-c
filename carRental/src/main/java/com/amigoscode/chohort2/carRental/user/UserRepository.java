package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CRJpaRepository<User,Long> {
}
