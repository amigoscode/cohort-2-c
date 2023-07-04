package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarProviderUserService {
    private final CarProviderUserRepository carProviderUserRepository;

    public CarProviderUser saveCarProviderUser(CarProviderUser carProviderUser) {
        return carProviderUserRepository.save(carProviderUser);
    }
    public CarProviderUser findCarProviderUserByUserId(Long id) {
        return carProviderUserRepository.findCarProviderUserByUserId(id)
                .orElseThrow(()-> new ApiRequestException(ErrorConstants.CAR_PROVIDER_USER));
    }
}
