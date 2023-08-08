package com.amigoscode.chohort2.carRental.external.s3;


import com.amigoscode.chohort2.carRental.image.ImageS3Handler;
import com.amigoscode.chohort2.carRental.image.MultiMediaS3Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;

@Service
public class S3Service implements MultiMediaS3Handler.S3Service {

    private final S3Client s3;


    public S3Service(@Autowired S3Client s3) {
        this.s3 = s3;
    }

    @Override
    public void putObject(String bucketName, String key, byte[] file, Map<String, String> metadata) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .metadata(metadata)
                .build();
        s3.putObject(request, RequestBody.fromBytes(file));
    }

    public void putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(request, RequestBody.fromBytes(file));
    }
    @Override
    public void deleteObject (String bucketName, String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.deleteObject(request);
    }
    @Override
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


}
