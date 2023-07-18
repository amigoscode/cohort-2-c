package com.amigoscode.chohort2.carRental.external.s3;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableAsync
public class S3Config {

    @Value("${aws.s3.mock}")
    private boolean mock;

    @Value("${aws.sso-profile}")
    private String ssoProfile;
    @Bean
    public AwsCredentialsProvider getSSOProvider(){
        return ProfileCredentialsProvider
                .builder()
                .profileName(ssoProfile).build();

    }
//    @Bean
//    public AwsCredentialsProvider awsCredentialsProvider (){
//
//        return DefaultCredentialsProvider
//                .builder()
//                .asyncCredentialUpdateEnabled(false)
//                .reuseLastProviderEnabled(true)
//                .profileName(ssoProfile)
//                .build();
//    }
    @Bean
    public S3Client s3Client() {
        if (false) {
            return new S3Fake();
        }
        return S3Client.builder()
                .credentialsProvider(getSSOProvider())
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .build();
    }



}