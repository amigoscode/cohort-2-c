package com.amigoscode.chohort2.carRental.userAddress;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class UserAddressServices {

    private final UserAddressRepository userAddressRepository;
}
