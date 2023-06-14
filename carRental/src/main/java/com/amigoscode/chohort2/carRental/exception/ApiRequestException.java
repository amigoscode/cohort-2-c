package com.amigoscode.chohort2.carRental.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
public class ApiRequestException extends RuntimeException {

    private String message;

    private String errorKey;

    private Object[] messageArgs;

    private int statusCode = HttpStatus.BAD_REQUEST.value();

    public ApiRequestException(String errorKey) {
        this(null, errorKey);
    }

    public ApiRequestException(String message, String errorKey) {
        super(errorKey);
        this.message = message;
        this.errorKey = errorKey;

    }

    public ApiRequestException message(String message) {
        this.setMessage(message);
        return this;
    }

    public ApiRequestException errorKey(String errorKey) {
        this.setErrorKey(errorKey);
        return this;
    }


    public ApiRequestException statusCode(int statusCode) {
        this.setStatusCode(statusCode);
        return this;
    }

    public ApiRequestException messageArgs(Object[] messageArgs) {
        this.setMessageArgs(messageArgs);
        return this;
    }


}
