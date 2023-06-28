package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarBookingService {

private final CarBookingRepository carBookingRepository;

}
