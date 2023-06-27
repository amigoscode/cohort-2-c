package com.amigoscode.chohort2.carRental.city;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
}
