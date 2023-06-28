package com.amigoscode.chohort2.carRental.carBooking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/car-bookings")
public class CarBookingController {
    private final CarBookingService carBookingService;

}
