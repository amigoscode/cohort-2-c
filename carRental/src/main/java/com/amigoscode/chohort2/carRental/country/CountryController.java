package com.amigoscode.chohort2.carRental.country;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

}
