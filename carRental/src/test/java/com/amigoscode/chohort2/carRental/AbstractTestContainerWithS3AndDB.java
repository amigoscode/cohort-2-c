package com.amigoscode.chohort2.carRental;

import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Config;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@RunWith(SpringRunner.class)
public abstract class AbstractTestContainerWithS3AndDB extends AbstractTestContainerWithDB {

    @Autowired
    S3Config s3Config;

    @Autowired
    S3Service s3Service;

    @Autowired
    S3Buckets s3Buckets;

    public AbstractTestContainerWithS3AndDB() {
        this(new HashMap<>(), new ArrayList<>());
    }

    public AbstractTestContainerWithS3AndDB(Map<String, Supplier<Object>> sources, List<Startable> services) {
        super(new HashMap<>() {{
            putAll(dynamicSourceProperties);
            putAll(sources);
        }}, new ArrayList<>() {{
            add(s3container);
            addAll(services);
        }});
    }

    @Container
    protected static final LocalStackContainer s3container = new LocalStackContainer(DockerImageName.parse("localstack/localstack:2.2.0"))
            .withServices(LocalStackContainer.Service.S3)
           .withFileSystemBind("localstack","/var/lib/localstack")
            .withLogConsumer(new Slf4jLogConsumer(log));
    private static final Map<String, Supplier<Object>> dynamicSourceProperties = new HashMap<>(){{


        put("aws.s3.test_access_key_id", s3container::getAccessKey);
        put("aws.s3.test_secret_access_key", s3container::getSecretKey);
        put("aws.s3.test_endpoint",()->s3container.getEndpointOverride(S3));
    }};

//    private final static String BUCKET_NAME = "images";
//
//    @BeforeAll
//    static void beforeAll() throws IOException, InterruptedException {
//        s3container.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", BUCKET_NAME, "--region", s3container.getRegion());
//    }



//    @Override
//    void resolveDynamicProperties(){
//        super.resolveDynamicProperties();
//        Map<String, Supplier<Object>> properties = new HashMap<>(){{
//            put("aws.s3.test_endpoint",()-> s3container.getEndpointOverride(LocalStackContainer.Service.S3).toString());
//            put("aws.s3.test_access_key_id", s3container::getAccessKey);
//            put("aws.s3.test_secret_access_key", s3container::getSecretKey);
//            put("aws.s3.test_region", s3container::getRegion);
//        }};
//        resolveMultiDynamicPropertySources(postgreSQLContainer,properties);
//    }
//    @Bean
//    @Qualifier("s3Client")
//    protected static S3Client s3ClientMock()
//    {
//        return S3Client.builder()
//                .region(Region.of(s3container.getRegion()))
//                .endpointOverride(s3container.getEndpointOverride(LocalStackContainer.Service.S3))
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(AwsBasicCredentials.create(s3container.getAccessKey(), s3container.getSecretKey()))
//                ).build();
//    }



    //    protected RequestSpecification requestSpecification;

//    @LocalServerPort
//    protected int localServerPort;
//
//    @BeforeEach
//    public void setUpAbstractTestContainerWithS3 (){
//        requestSpecification = new RequestSpecification().setPort(localServerPort).addHeader()
//    }



}
