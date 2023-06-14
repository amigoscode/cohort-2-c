package com.amigoscode.chohort2.carRental.constants;


public final class Constants {
    public static final String SYSTEM = "System";

    public static final String ANONYMOUS_USER = "anonymousUser";

    public static final String USERNAME_REGEX = "^(?=.{3,50}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";


    private Constants() {
    }
}
