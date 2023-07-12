package com.amigoscode.chohort2.carRental.logging;

import com.amigoscode.chohort2.carRental.car.CarMapper;
import com.amigoscode.chohort2.carRental.constants.LoggingConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class ControllerLoggingAspect extends AbstractAspect {

    @Pointcut("bean(*Controller) && inProjectPoint()")
    void inAnyControllerPoint() {
    }

    @Pointcut ("inAnyControllerPoint() && anyPublicOperation()")
    void publicControllerOperation (){
    }

    @Pointcut ("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    void anyPostOperation (){
    }

    @Pointcut ("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    void anyGetOperation (){
    }

    @Pointcut ("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    void anyDeleteOperation (){
    }

    @Pointcut ("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    void anyPutOperation (){
    }
    @Pointcut ("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    void anyPatchOperation (){
    }


/**
 * @deprecated beware that using this method in production might potentially lead to security risks because the passed
 *   object is logged before being validated.
 * */
    @Before("publicControllerOperation() && (anyPostOperation() || anyPutOperation() || anyPatchOperation() || anyDeleteOperation())")
    void logBeforePost(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info(LoggingConstants.CALLED_WITH_ARGS, methodName, args);
    }

    @Before("publicControllerOperation() && anyGetOperation()")
    void logBeforeGet(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        log.trace(LoggingConstants.CALLED_WITH_ARGS, methodName, args);
    }
    @AfterReturning (value = "publicControllerOperation()", returning = "retVal")
    void logAfterReturning(JoinPoint joinPoint, ResponseEntity retVal){
        String methodName = joinPoint.getSignature().getName();
        log.trace(LoggingConstants.SUCCESS_AND_RETURN, methodName, retVal.getBody());
    }


}
