package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
