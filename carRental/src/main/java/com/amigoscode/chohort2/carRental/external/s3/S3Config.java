package com.amigoscode.chohort2.carRental.external.s3;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

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
//        return CustomCredentialsProvider.builder()
//                .profileName(ssoProfile)
//                .reuseLastProviderEnabled(true)
//                .asyncCredentialUpdateEnabled(false)
//                .build();
//    }
    @Bean
    @Profile({"dev"})
    @Qualifier("s3Client")
    public S3Client s3Client() {

        return S3Client.builder()
                .credentialsProvider(getSSOProvider())
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .build();
    }

    @Bean
    @Profile("test")
    @Qualifier("s3Client")
    public S3Client s3ClientMock (){
        return new S3Client(){

            @Override
            public String serviceName() {
                return "fake";
            }

            @Override
            public void close() {

            }
        };
    }




}