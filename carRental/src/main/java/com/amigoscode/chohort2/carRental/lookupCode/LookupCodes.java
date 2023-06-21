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



}
