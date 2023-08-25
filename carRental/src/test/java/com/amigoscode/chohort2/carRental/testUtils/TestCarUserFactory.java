package com.amigoscode.chohort2.carRental.testUtils;

import com.amigoscode.chohort2.carRental.carUser.CarUser;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicenseService;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestCarUserFactory {

    private static int testCarUserSeq = 0;

    @Autowired
    UserService userService;
    @Autowired
    DriverLicenseService driverLicenseService;

    public static CarUser createTestCarUser(){
        return null;
    }
    @Transactional
    public User generateFullyFledgedUser(User user, DriverLicense driverLicense) {
        User tmpUser = userService.save(user);
        driverLicense.setUserId(tmpUser.getId());
        driverLicenseService.save(driverLicense);
        return tmpUser;
    }
}
