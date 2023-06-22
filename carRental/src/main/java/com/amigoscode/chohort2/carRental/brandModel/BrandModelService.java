package com.amigoscode.chohort2.carRental.brandModel;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class BrandModelService {

    private final BrandModelRepository brandModelRepository;
}
