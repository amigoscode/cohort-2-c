package com.amigoscode.chohort2.carRental.annotation;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@Service
public @interface TransactionalService {
}
