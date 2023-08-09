package com.amigoscode.chohort2.carRental.testUtils;


import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes.*;
import com.amigoscode.chohort2.carRental.user.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUserFactory {

    private static int testUserSequence = 0;

    public static User createTestUser(Integer userType, Integer userStatus, Authority... authorities) {
        return createTestUser(
                String.join("", Arrays.stream(authorities)
                .map(Object::toString)
                .collect(Collectors.toSet())))
                .setTypeCode(userType)
                .setStatusCode(userStatus)
                .setAuthorities(new HashSet<>(Arrays.asList(authorities)));
    }

    public static User createCarProviderTestUser() {
        return createTestUser("carProvider")
                .setTypeCode(UserType.carProvider)
                .setStatusCode(UserStatus.active)
                .setAuthorities(Set.of(new Authority().setName(AuthorityConstants.CAR_PROVIDER)));
    }

    public static User createCarProviderTestUser(Integer userStatus) {
        return createTestUser("carProvider")
                .setTypeCode(UserType.carProvider)
                .setStatusCode(userStatus)
                .setAuthorities(Set.of(new Authority().setName(AuthorityConstants.CAR_PROVIDER)));
    }


    public static User createClientTestUser() {
        return createTestUser("client")
                .setTypeCode(UserType.client)
                .setStatusCode(UserStatus.active)
                .setAuthorities(Set.of(new Authority().setName(AuthorityConstants.CLIENT)));

    }

    public static User createClientTestUser(Integer userStatus) {
        return createTestUser("client")
                .setTypeCode(UserType.client)
                .setStatusCode(userStatus)
                .setAuthorities(Set.of(new Authority().setName(AuthorityConstants.CLIENT)));

    }


    public static User createAdminTestUser() {
        return createTestUser("admin")
                .setTypeCode(UserType.admin)
                .setStatusCode(UserStatus.active)
                .setAuthorities(Set.of(new Authority().setName(AuthorityConstants.CLIENT)));

    }

    public static User createAdminTestUser(Integer userStatus) {
        return createTestUser("admin")
                .setTypeCode(UserType.admin)
                .setStatusCode(userStatus)
                .setAuthorities(Set.of(new Authority().setName(AuthorityConstants.ADMIN)));
    }

    private static User createTestUser(String userType) {

        testUserSequence++;
        String prefix = String.join("",userType,String.valueOf(testUserSequence));
        return new User()
                .setUsername(prefix+"UserName")
                .setFirstName(prefix +"FirstName")
                .setLastName(prefix + "LastName")
                .setEmail(prefix + "@gmail.com")
                .setNin("NIN" + prefix)
                .setPassword(prefix+"password");
    }
}
