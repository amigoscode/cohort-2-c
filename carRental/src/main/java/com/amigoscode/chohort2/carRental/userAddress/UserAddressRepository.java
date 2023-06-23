package com.amigoscode.chohort2.carRental.userAddress;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAddressRepository extends CRJpaRepository<UserAddress, Long> {
}
