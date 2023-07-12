package com.amigoscode.chohort2.carRental.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DataAccessLoggingAspect extends AbstractAspect{

    @Pointcut("bean(*Repository)  && inProjectPoint()")
    void inAnyRepositoryPoint() {
    }
    @Pointcut ("inAnyRepositoryPoint() && anyPublicOperation()")
    void publicServiceOperation (){
    }
}
