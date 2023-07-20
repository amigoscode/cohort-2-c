package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MultiMediaS3Handler<ID> {



    S3Buckets getBuckets();

    S3Service getS3Service();


    String getMEDIA_TYPE();

    byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException;
    @Transactional
    default String uploadFile(ID domainId, String S3DomainName, MultipartFile file) {

        String domainFileId = UUID.randomUUID().toString();
        try {
            getS3Service().putObject(
                    getBuckets().getDomain(),
                    "%s/%s/%s/%s".formatted(getMEDIA_TYPE(), S3DomainName, domainId, domainFileId),
                    file.getBytes()
            );
        } catch (IOException e) {

            //TODO: change exception type
            throw new RuntimeException("failed to upload %S %S".formatted(S3DomainName, getMEDIA_TYPE()), e);
        }

        return domainFileId;
    }

    default byte[] getResizedFile(ID domainId, String S3DomainName, String FileUrl) {
        try {
            return resizeFile(getFile(domainId, S3DomainName, FileUrl), getS3Service().getCarResizeMagnitude());

            //TODO: change exception handling
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default byte[] getOriginalUnresizedFile(ID domainId, String S3DomainName, String FileUrl) {

        return getFile(domainId, S3DomainName, FileUrl);
    }

    private byte[] getFile(ID domainId, String S3DomainName, String FileUrl) {
        return getS3Service().getObject(
                getBuckets().getDomain(),
                "%s/%s/%s/%s".formatted(getMEDIA_TYPE(), S3DomainName, domainId, FileUrl)
        );
    }
}
