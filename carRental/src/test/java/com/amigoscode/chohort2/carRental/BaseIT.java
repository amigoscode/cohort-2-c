package com.amigoscode.chohort2.carRental;

import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderService;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;
import com.amigoscode.chohort2.carRental.security.jwt.JWTUtil;
import com.amigoscode.chohort2.carRental.testUtils.*;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserRepository;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@ActiveProfiles("test")
public class BaseIT extends AbstractTestContainer {

    static CarProviderUser carProviderUser1;
    static CarProviderUser carProviderUser2;
    static User user1;
    static User user2;
    static User adminTestUser1;
    static User adminTestUser2;
    static User clientTestUser1;
    static User clientTestUser2;
    static CarProvider carProvider1;
    static CarProvider carProvider2;
    static DriverLicense driverLicense1;
    static DriverLicense driverLicense2;
    static User client1;
    static User client2;

    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TestCarProviderUserFactory carProviderUserFactory;
    @Autowired
    protected TestCarUserFactory carUserFactory;
    @Autowired
    protected JWTUtil jwtUtil;

    private final static String [] BUCKET_NAMES = {
            "rental-car-sandbox-original",
            "rental-car-sandbox-browser",
            "rental-car-sandbox-mobile"
    };

    protected static final String BASE_URL = "api/v1/";

    @BeforeAll
    public static void generate() {

        Arrays.stream(BUCKET_NAMES).forEach(bucketName->
        {
            try {
                localstack.execInContainer("awslocal", "s3api" ,"create-bucket", "--bucket", bucketName, "--region", "us-east-1");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        user1 = TestUserFactory.createCarProviderTestUser();
        user2 = TestUserFactory.createCarProviderTestUser();
        carProviderUser1 = TestCarProviderUserFactory.createCarProviderUser();
        carProviderUser2 = TestCarProviderUserFactory.createCarProviderUser();
        adminTestUser1 = TestUserFactory.createAdminTestUser();
        adminTestUser2 = TestUserFactory.createAdminTestUser();
        clientTestUser1 = TestUserFactory.createClientTestUser();
        clientTestUser2 = TestUserFactory.createClientTestUser();
        carProvider1 = TestCarProviderFactory.createCarProvider();
        carProvider2 = TestCarProviderFactory.createCarProvider();
        driverLicense1 = TestDriverLicenseFactory.createTestDriverLicense();
        driverLicense2 = TestDriverLicenseFactory.createTestDriverLicense();
        client1 = TestUserFactory.createClientTestUser();
        client2 = TestUserFactory.createClientTestUser();

    }

    @BeforeEach
    public void tearUp() {

    }


    @AfterEach
    public void tearDown() {

    }
    protected List<CarProviderUser> setUpCarProviders() {
        List <CarProviderUser> carProviderUsers = new ArrayList<>();
        carProviderUsers.add(carProviderUserFactory.generateFullyFledgedCarProvider(user1, carProviderUser1, carProvider1));
        carProviderUsers.add(carProviderUserFactory.generateFullyFledgedCarProvider(user2, carProviderUser2, carProvider2));
        return carProviderUsers;
    }

    protected List<User> setUpClients(){
        List<User> clients = new ArrayList<>();
        clients.add(carUserFactory.generateFullyFledgedUser(client1,driverLicense1));
        clients.add(carUserFactory.generateFullyFledgedUser(client2,driverLicense2));
        return clients;
    }

    protected String getJwtTokenForUser(User user) {
        Set<Authority> authorities = user.getAuthorities();
        String token = jwtUtil.issueToken(user.getUsername(), authorities.stream()
                .map(auth-> new SimpleGrantedAuthority(auth.getName()))
                .toList());
        return "Bearer " + token;
    }

    protected Consumer<HttpHeaders> setHeaders(String jwtToken){
        return httpHeaders -> {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
            httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtToken);
            httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
        };
    }


    protected Consumer<HttpHeaders> setHeaders(String jwtToken, MediaType contentType) {
        return httpHeaders -> {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, contentType.getType());
            httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtToken);
            httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
        };
    }

    protected Consumer<HttpHeaders> setHeaders (String jwtToken, MediaType contentType, MediaType accept){
        return httpHeaders -> {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, contentType.getType());
            httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtToken);
            httpHeaders.add(HttpHeaders.ACCEPT, accept.getType());
        };
    }

}
