package com.amigoscode.chohort2.carRental.testUtils;

import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderService;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class TestCarProviderFactory {



    private static int carProviderSequence = 0;

    public static CarProvider createCarProvider(){
        carProviderSequence++;
        return new CarProvider()
                .setName("TestCarProvider"+carProviderSequence)
                .setCrNumber("TestCrNumber"+carProviderSequence);
    }


}

