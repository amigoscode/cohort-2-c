package com.amigoscode.chohort2.carRental.country;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

}
