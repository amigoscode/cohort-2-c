package com.amigoscode.chohort2.carRental.city;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
}
