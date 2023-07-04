package com.amigoscode.chohort2.carRental.carProvider;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/car-providers")
@RequiredArgsConstructor
public class CarProviderController {
    private final Logger log = LoggerFactory.getLogger(CarProviderController.class);

    private final CarProviderService carProviderService;



}
