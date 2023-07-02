package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarProviderUserService {
    private final CarProviderUserRepository carProviderUserRepository;

    public CarProviderUser saveCarProviderUser(CarProviderUser carProviderUser) {
        return carProviderUserRepository.save(carProviderUser);
    }
}
