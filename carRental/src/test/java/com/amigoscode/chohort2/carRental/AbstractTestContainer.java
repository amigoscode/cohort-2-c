package com.amigoscode.chohort2.carRental;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:tc:postgresql:latest:///integration-tests-db")
@Slf4j
public abstract class AbstractTestContainer {

//    @Container
//    protected static final   PostgreSQLContainer<?> postgreSQLContainer =
//            new PostgreSQLContainer<>("postgres:latest")
//                    .withDatabaseName("unit-test")
//                    .withUsername("root")
//                    .withPassword("123");
//
//    @DynamicPropertySource
//    private static void registerDataSourceProperties(DynamicPropertyRegistry registry){
//
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }
//
//    protected static DataSource getDataSource(){
//        return DataSourceBuilder
//                .create()
//                .driverClassName(postgreSQLContainer.getDriverClassName())
//                .url(postgreSQLContainer.getJdbcUrl())
//                .username(postgreSQLContainer.getUsername())
//                .password(postgreSQLContainer.getPassword())
//                .build();
//    }

    @Container
    protected static final LocalStackContainer localstack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:2.2.0"))
            .withServices(S3)
            .withExposedPorts(4566);


    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        log.debug("====================================================================" +
                "==========" + localstack.getMappedPort(4566) + "============" +
                "========================================================================");
        registry.add("aws.s3.test_access_key_id", localstack::getAccessKey);
        registry.add("aws.s3.test_secret_access_key", localstack::getSecretKey);
        registry.add("aws.s3.test_endpoint", () -> localstack.getEndpointOverride(S3));
        registry.add("aws.s3.test_region", ()-> "us-east-1");

    }

}
