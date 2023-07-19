package com.amigoscode.chohort2.carRental.external.s3;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3;

    @Value("${aws.s3.car.resize-magnitude}")
    private int carResizeMagnitude;
    @Value("${aws.s3.car.domain}")
    private String carDomain;


    public S3Service(S3Client s3) {
        this.s3 = s3;
    }

    @Async
    public void putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(request, RequestBody.fromBytes(file));
    }

    public void deleteObject (String bucketName, String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.deleteObject(request);
    }

    public byte[] getObject(String bucketName, String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> res = s3.getObject(request);

        try {
            return res.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int getCarResizeMagnitude() {
        return carResizeMagnitude;
    }

    public void setCarResizeMagnitude(int carResizeMagnitude) {
        this.carResizeMagnitude = carResizeMagnitude;
    }

    public String getCarDomain() {
        return carDomain;
    }

    public void setCarDomain(String carDomain) {
        this.carDomain = carDomain;
    }
}
