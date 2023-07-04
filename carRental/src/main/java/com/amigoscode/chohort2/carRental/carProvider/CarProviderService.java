package com.amigoscode.chohort2.carRental.carProvider;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.security.SecurityUtils;
import com.amigoscode.chohort2.carRental.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@TransactionalService
@RequiredArgsConstructor
public class CarProviderService {

    private final CarProviderRepository carProviderRepository;
    private final UserService userService;

    public CarProvider saveCarProvider(CarProvider carProvider) {
        return carProviderRepository.save(carProvider);
    }



}
