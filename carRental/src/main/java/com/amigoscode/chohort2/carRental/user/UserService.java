package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getLoggedInUser() {
        String username = SecurityUtils.getCurrentUsername();
        return userRepository.findByUsernameWithAuthorities(username).orElseThrow(() -> new ApiRequestException(ErrorConstants.USER_NOT_FOUND));
    }




}
