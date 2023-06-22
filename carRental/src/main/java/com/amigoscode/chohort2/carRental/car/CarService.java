package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
}
