package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class SomeTestIT extends AbstractTestContainer {

    CarRepository carRepository;

    private final static String [] BUCKET_NAMES = {
            "rental-car-sandbox-original",
            "rental-car-sandbox-browser",
            "rental-car-sandbox-mobile"
    };
    @BeforeAll
    static void beforeAll() {
        Arrays.stream(BUCKET_NAMES).forEach(bucketName ->
        {
            try {
                localstack.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", bucketName, "--region", "us-east-1");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void doTest()
    {
        carRepository.findById(10L);
    }}
