package com.amigoscode.chohort2.carRental.logging;

import com.amigoscode.chohort2.carRental.constants.LoggingConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class ServiceLoggingAspect extends AbstractAspect{
    @Pointcut("bean(*Service)  && inProjectPoint()")
    void inAnyServicePoint() {
    }

    @Pointcut ("inAnyServicePoint() && anyPublicOperation()")
    void publicServiceOperation (){
    }

    @Before("publicServiceOperation()")
    void logBeforePublicOperations(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        log.trace(LoggingConstants.CALLED_WITH_ARGS, methodName, args);
    }
}
