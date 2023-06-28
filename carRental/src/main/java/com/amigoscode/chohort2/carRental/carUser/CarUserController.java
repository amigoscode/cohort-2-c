package com.amigoscode.chohort2.carRental.carUser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/car-users")
@RequiredArgsConstructor
public class CarUserController {
    private CarUserService carUserService;
}
