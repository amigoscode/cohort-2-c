package com.amigoscode.chohort2.carRental.car;


import com.amigoscode.chohort2.carRental.AbstractTestContainerWithS3;
import com.amigoscode.chohort2.carRental.AbstractTestContainerWithS3AndDB;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import com.amigoscode.chohort2.carRental.image.MultiMediaS3Handler;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@RunWith(SpringRunner.class)
public class CarControllerIT extends AbstractTestContainerWithS3AndDB {

    @Autowired
    private  CarRepository carRepository;
    @Autowired
    private CarProviderUserService carProviderUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private S3Buckets s3Buckets;
    @Autowired
    private MultiMediaS3Handler.S3ObjectDomain carS3ObjectDomain;

        private final static String BUCKET_NAME = "images";
        @BeforeAll
        static void beforeAll() throws IOException, InterruptedException {
       // s3container.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", BUCKET_NAME, "--region", s3container.getRegion());

    }

    @Test
    public void contextLoads(){
        System.out.println(s3Buckets.getS3domains().toString());
    }
    @Test
    public void doTest() {
            s3Service.getObject("images","1231421");
    }
}
