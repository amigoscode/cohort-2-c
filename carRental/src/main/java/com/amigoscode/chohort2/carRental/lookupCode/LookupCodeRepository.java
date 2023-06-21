package com.amigoscode.chohort2.carRental.lookupCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupCodeRepository extends JpaRepository<LookupCode, Long> {
    List<LookupCode> findAllByKey(String key);

    Optional<LookupCode> findByKeyAndCode(String key, Integer code);
}
