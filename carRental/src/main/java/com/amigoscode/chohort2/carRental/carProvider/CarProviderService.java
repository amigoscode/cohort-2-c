package com.amigoscode.chohort2.carRental.carProvider;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarProviderService {

    private final CarProviderRepository carProviderRepository;

    public CarProvider saveCarProvider(CarProvider carProvider) {
       return carProviderRepository.save(carProvider);
    }
}
