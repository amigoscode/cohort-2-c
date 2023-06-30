package com.amigoscode.chohort2.carRental.validation;

import com.amigoscode.chohort2.carRental.exception.ApiRequestException;

import java.util.function.Supplier;

public abstract class Validator {

    public static void invalidateIfTure(Supplier<Boolean> supplier, String message) {
        invalidateIfTure(supplier, null,message);
    }





    public static void invalidateIfTure(Supplier<Boolean> supplier, String errorKey, String message, Object... args) {
        if (supplier.get()) {
            throw new ApiRequestException(message, errorKey)
                    .messageArgs(args);
        }

    }

    public static void invalidateIfTure(Supplier<Boolean> supplier, ApiRequestException e) {
        if (supplier.get()) {
            throw e;
        }
    }


}
