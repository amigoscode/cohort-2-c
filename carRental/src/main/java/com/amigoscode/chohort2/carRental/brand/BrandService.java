package com.amigoscode.chohort2.carRental.brand;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

}
