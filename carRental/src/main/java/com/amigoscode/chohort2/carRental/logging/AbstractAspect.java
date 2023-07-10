package com.amigoscode.chohort2.carRental.logging;

import org.aspectj.lang.annotation.Pointcut;

public class AbstractAspect {

    @Pointcut("execution(public * *(..))")
    void anyPublicOperation() {}

    @Pointcut ("within(com.amigoscode.chohort2.carRental..*)")
    void inProjectPoint(){}

}
