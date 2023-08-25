package com.amigoscode.chohort2.carRental.external.s3;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.*;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.sso-profile}")
    private String ssoProfile;

    @Value("${aws.s3.test_access_key_id}")
    private String testAccessKeyId;
    @Value("${aws.s3.test_secret_access_key}")
    private String testSecretAccessKey;
    @Value("${aws.s3.test_region}")
    private String testRegion;
    @Value("${aws.s3.test_endpoint}")
    private String testEndpoint;

    @Bean
    public ProfileCredentialsProvider getSSOProvider() {
        return ProfileCredentialsProvider
                .builder()
                .profileName(ssoProfile).build();

    }

    //    @Bean
//    public AwsCredentialsProvider awsCredentialsProvider (){
//
//        return CustomCredentialsProvider.builder()
//                .profileName(ssoProfile)
//                .reuseLastProviderEnabled(true)
//                .asyncCredentialUpdateEnabled(false)
//                .build();
//    }
    @Bean
    @Profile({"dev"})
    @Qualifier("s3Client")
    public S3Client s3Client(ProfileCredentialsProvider ssoProvider) {

        return S3Client.builder()
                .credentialsProvider(ssoProvider)
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .build();
    }


    /**
     * Provides credentials for LOCALSTACK. Both values are "test" because it's recommended by LocalStack
     * to make pre-singed URLs work.
     */
    @Bean
    @Profile({"test"})
    public AwsBasicCredentials getTestCredentials() {
        return AwsBasicCredentials.create(testAccessKeyId, testSecretAccessKey);
    }
    @Bean
    @Profile("test")
    @Qualifier("s3Client")
    public S3Client s3ClientMock(AwsBasicCredentials testCredentials) {
        return S3Client.builder()
                .region(Region.of(testRegion))
                .endpointOverride(URI.create(testEndpoint))
                .credentialsProvider(
                        StaticCredentialsProvider.create(testCredentials)
                ).build();
    }


}