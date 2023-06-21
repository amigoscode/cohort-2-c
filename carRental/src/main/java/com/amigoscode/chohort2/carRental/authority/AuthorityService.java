package com.amigoscode.chohort2.carRental.authority;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

}
