package com.amigoscode.chohort2.carRental.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiExceptionResponse {

    private String path;
    private String message;
    private int statusCode;
    private String errorKey;
    private  String userMessage;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
