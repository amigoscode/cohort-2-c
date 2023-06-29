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




    private ErrorConstants() {
    }
}
