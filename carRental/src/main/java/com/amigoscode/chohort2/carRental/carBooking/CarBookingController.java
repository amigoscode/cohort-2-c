package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.carBooking.vm.UserBookingVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/car-bookings")
public class CarBookingController {
    private final CarBookingService carBookingService;


    @PostMapping
    @Secured({AuthorityConstants.CLIENT})
    public ResponseEntity<CarBookingDTO> createUserBooking(@RequestBody @Valid UserBookingVM userBookingVM){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carBookingService.createUserBooking(userBookingVM));
    }

}
