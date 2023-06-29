package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CRJpaRepository<User,Long> {
    @Query("select u from User u join FETCH u.authorities where u.username=:username")
    Optional<User> findByUsernameWithAuthorities(@Param("username") String username);
}
