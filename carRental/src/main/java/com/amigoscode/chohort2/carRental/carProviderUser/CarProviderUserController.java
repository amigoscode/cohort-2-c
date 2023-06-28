package com.amigoscode.chohort2.carRental.carProviderUser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/car-providers")
@RequiredArgsConstructor
public class CarProviderUserController {
    private final CarProviderUserService carProviderUserService;
}
