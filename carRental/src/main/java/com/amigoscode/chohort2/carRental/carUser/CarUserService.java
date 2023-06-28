package com.amigoscode.chohort2.carRental.carUser;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarUserService {

    private final CarUserRepository userRepository;


}
