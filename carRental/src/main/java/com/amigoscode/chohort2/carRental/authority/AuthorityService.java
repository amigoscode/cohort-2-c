package com.amigoscode.chohort2.carRental.authority;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public Authority findByName(String authName) {
        return authorityRepository.findById(authName)
                .orElseThrow(()-> new ApiRequestException("authority not found"));
    }
}
