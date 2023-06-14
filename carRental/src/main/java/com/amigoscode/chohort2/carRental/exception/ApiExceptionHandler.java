package com.amigoscode.chohort2.carRental.exception;

import com.amigoscode.chohort2.carRental.constants.ErrorConstants;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final MessageSource messageSource;
    private final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);



    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<ApiExceptionResponse> handelRequestException(ApiRequestException e, HttpServletRequest request) {


        return ResponseEntity
                .status(e.getStatusCode())
                .body(ApiExceptionResponse
                        .builder()
                        .path(request.getRequestURI())
                        .message(e.getMessage())
                        .statusCode(e.getStatusCode())
                        .errorKey(e.getErrorKey())
                        .userMessage(e.getMessage())
                        .build());

    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiExceptionResponse> handelArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {


        log.error("Error: ",e);
        return ResponseEntity
                .badRequest()
                .body(ApiExceptionResponse
                        .builder()
                        .path(request.getRequestURI())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Inputs to API: " + request.getRequestURI())
                        .userMessage(e.getMessage())
                        .build());

    }




    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiExceptionResponse> handelApplicationException(Exception e, HttpServletRequest request) {

        log.error("Error: ",e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiExceptionResponse
                        .builder()
                        .path(request.getRequestURI())
                        .message(e.getMessage())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .userMessage(ErrorConstants.INTERNAL)
                        .build());

    }



}
