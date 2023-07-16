package com.amigoscode.chohort2.carRental.constants;

public class ErrorConstants {
    public static final String BASE_ERROR_KEY = "error.";

    public static final String NOT_UNAUTHORIZED = String
            .format("%s%s", BASE_ERROR_KEY, "unauthorized");
    public static final String INTERNAL = String
            .format("%s%s", BASE_ERROR_KEY, "internal");

    public static final String FIELD_MISSING = String
            .format("%s%s", BASE_ERROR_KEY, "field.missing");

    public static final String FORBIDDEN = String
            .format("%s%s", BASE_ERROR_KEY, "forbidden");

    public static final String LOGIN = String
            .format("%s%s", BASE_ERROR_KEY, "login");

    public static final String NOT_VALID_LICENSE = String
            .format("%s%s", BASE_ERROR_KEY, "notValidLicense");



    //-----------Not found errors key---------------
    public static final String BASE_NOT_FOUND_KEY = String
            .format("%s%s", BASE_ERROR_KEY, "notFound.");

    public static final String USER_NOT_FOUND = String
            .format("%s%s", BASE_NOT_FOUND_KEY, "user");

    public static final String CAR_NOT_FOUND = String
            .format("%s%s", BASE_NOT_FOUND_KEY, "car");
    public static final String CAR_PROVIDER_USER = String
            .format("%s%s", BASE_NOT_FOUND_KEY, "carProviderUser");
    public static final String IMAGE_NOT_FOUND = String
            .format("%s%s", BASE_NOT_FOUND_KEY, "image");




    private ErrorConstants() {
    }
}
