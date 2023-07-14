package com.amigoscode.chohort2.carRental.lookupCode;

public interface LookupCodes {


    interface UserType {
        String KEY = "user_type";
        Integer admin = 1;
        Integer carProvider = 2;
        Integer client = 3;

    }

    interface UserStatus {
        String KEY = "user_status";
        Integer active = 1;
        Integer inactive = 2;
        Integer blocked = 3;


    }

    interface CarCategory {
        String KEY = "car_category";

        Integer luxury = 1;

        Integer economy = 2;
    }

    interface CarBookingStatus {

        String KEY = "car_booking_status";

        Integer available = 1;
        Integer inUse = 2;
    }

    interface MediumCode {
        String KEY = "medium_code";
        Integer portal = 1;
        Integer email = 2;
        Integer sms = 3;
    }
     interface UserBookingStatus {
        String KEY = "user_booking_status";
        Integer inUse  = 1;
        Integer returned  = 2;

    }

    interface SortingType {
        int asc = 1;
        int desc = 2;
    }

    interface CarSearchOrderByType {

        int carProviderId = 1;
        int registrationNumber = 2;
        int brandCode = 3;
        int brandModelCode = 4;
        int productionYear = 5;
        int maxSpeed = 6;
        int horsePower = 7;
        int rgbCode = 8;
        int description = 9;
        int price = 10;
        int categoryCode = 11;


    }


}
