package com.amigoscode.chohort2.carRental.auth;


import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;



    public String login(String username, String password) {
        log.info("user try to login username:{}", username);

        authenticate(username, password);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        List<SimpleGrantedAuthority> userAuthorities = userDetails
                .getAuthorities()
                .stream()
                .map(auth-> new SimpleGrantedAuthority(auth.getAuthority()))
                .toList();


        return jwtUtil.issueToken(username,userAuthorities);
    }





    private void authenticate(String username, String password) {


        try {
         authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));


        } catch (BadCredentialsException exception) {
            log.warn("user with username: {} is not authenticated", username);
            throw new ApiRequestException(exception.getMessage(), ErrorConstants.LOGIN);
        }
    }
}
