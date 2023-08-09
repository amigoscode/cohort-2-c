package com.amigoscode.chohort2.carRental.testUtils;

import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderService;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestCarProviderUserFactory {

    @Autowired
    CarProviderService carProviderService;
    @Autowired
    CarProviderUserService carProviderUserService;
    @Autowired
    UserService userService;
    private static int carProviderUserSeq = 0;

    public static CarProviderUser createAdminCarProviderUser(){
        return new CarProviderUser()
                .setCompanyAdmin(true);
    }

    public static CarProviderUser createCarProviderUser(){
        return new CarProviderUser()
                .setCompanyAdmin(false);
    }
    @Transactional
    public CarProviderUser generateFullyFledgedCarProvider(User user, CarProviderUser carProviderUser, CarProvider carProvider){
        User tmpUsr = userService.save(user);
        CarProvider tmpCarProvider = carProviderService.saveCarProvider(carProvider);
        var tmp = carProviderUserService.saveCarProviderUser(
                carProviderUser.setUserId(tmpUsr.getId())
                        .setCarProviderId(tmpCarProvider.getId())
        );

        return tmp;
    }
}
