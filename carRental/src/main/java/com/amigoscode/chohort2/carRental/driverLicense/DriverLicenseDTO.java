package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.user.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class DriverLicenseDTO {

    private Long id;

    private Long userId;

    private UserDTO userDTO;

    private int driverLicenseNumber;

    private int issued_in;

    private LocalDate issued_at;

    private LocalDate expiredDate;


}
