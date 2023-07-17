package com.amigoscode.chohort2.carRental.external.s3;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.utils.IoUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class S3Fake implements S3Client {


    // TODO: Check if this works for Windows users
    private static final String PATH =
            System.getProperty("user.home") + "/.amigoscode/s3";

    @Override
    public String serviceName() {
        return "fake";
    }

    @Override
    public void close() {

    }

    @Override
    public PutObjectResponse putObject(PutObjectRequest putObjectRequest,
                                       RequestBody requestBody)
            throws AwsServiceException, SdkClientException {
        InputStream inputStream = requestBody.contentStreamProvider().newStream();

        try {
            byte[] bytes = IoUtils.toByteArray(inputStream);
            Files.write(new File (
                    buildObjectFullPath(
                    putObjectRequest.bucket(),
                    putObjectRequest.key())).toPath() , bytes);

            return PutObjectResponse.builder().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getObject(
            GetObjectRequest getObjectRequest)
            throws  AwsServiceException, SdkClientException {

        try {
            FileInputStream fileInputStream = new FileInputStream(
                    buildObjectFullPath(
                            getObjectRequest.bucket(),
                            getObjectRequest.key())
            );
            return new ResponseInputStream<>(
                    GetObjectResponse.builder().build(),
                    fileInputStream
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildObjectFullPath(String bucketName, String key) {
        return PATH + "/" + bucketName + "/" + key;
    }
}
