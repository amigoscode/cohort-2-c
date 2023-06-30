package com.amigoscode.chohort2.carRental.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.stream.Stream;

public final class SecurityUtils {

    private SecurityUtils() {

    }


    public static Optional<String> getCurrentUsernameOpt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional
                .ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    if (principal instanceof UserDetails ud) {
                        return ud.getUsername();
                    }
                    if (principal instanceof String username) {
                        return username;
                    }
                    return null;
                });
    }


    public static String getCurrentUsername() {
        return getCurrentUsernameOpt()
                .orElseThrow(() -> new AccessDeniedException(HttpStatus.UNAUTHORIZED.getReasonPhrase()));
    }

    public static boolean isCurrentUserInRole(String role) {
        return getCurrentUserAuthorities()
                .map(grantedRole -> grantedRole.equals(role))
                .filter(hasTheAuthority -> hasTheAuthority)
                .findFirst()
                .orElse(false);

    }

    public static boolean isCurrentUserInAnyRole(String... roles) {
        for (String role : roles) {
            if (isCurrentUserInRole(role)) {
                return true;
            }
        }
        return false;
    }

    private static Stream<String> getCurrentUserAuthorities() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority);

    }


}
