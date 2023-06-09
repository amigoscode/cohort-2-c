package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.user.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class DriverLicenseDTO {

    private Long id;

    private Long userId;

    private UserDTO user;

    private String driverLicenseNumber;

    private Integer issuedIn;

    private LocalDate issuedAt;

    private LocalDate expiredDate;


}
