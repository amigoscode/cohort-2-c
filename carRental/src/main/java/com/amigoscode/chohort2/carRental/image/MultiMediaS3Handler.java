package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @param <T>  the type of domain owning image
 * @param <ID> the type of domain's id
 */

public abstract class MultiMediaS3Handler<T, ID> {


    final S3Service s3Service;
    private final S3Buckets s3Buckets;

    public MultiMediaS3Handler(S3Service s3Service,S3Buckets s3Buckets) {
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    S3Buckets getBuckets() {
        return s3Buckets;
    }

    S3Service getS3Service() {
        return s3Service;
    }

    abstract int getResizeMagnitude();

    abstract String getMEDIA_TYPE();


    abstract byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException;

    /**
     * Upload an image to S3.
     *
     * @param domainId id of object to be saved
     * @param domain   object that an image to be saved belongs to
     * @param file     object to be saved;
     * @throws RuntimeException
     */
    @Transactional
    public String uploadFile(ID domainId, String S3DomainName, MultipartFile file) {

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

    /**
     * Retrieve the image directly from S3 with the original resolution. Calling this method from public endpoint causes
     * a lot amount of egress traffic and subsequent charges. Use with caution.
     *
     * @param domainId id of object to be saved
     * @param domain   object that an image to be saved belongs to
     */

    public byte[] getOriginalUnresizedFile(ID domainId, String S3DomainName, String FileUrl) {

        return getFile(domainId, S3DomainName, FileUrl);
    }


    /**
     * Retrieve the image directly from S3 and resize it to save the egress traffic. The method is intended to be used
     * as a fallback if S3 offloading and/or cloud delivery network (Cloudflare, AWS Cloudformation) fails or
     * the triggered store-postprocessing hasn't finished by the time the image is requested.
     *
     * @param domainId id of object to be saved
     * @param domain   object that an image to be saved belongs to
     */
    public byte[] getResizedFile(ID domainId, String S3DomainName, String FileUrl) {
        try {
            return resizeFile(getFile(domainId, S3DomainName, FileUrl), getResizeMagnitude());

            //TODO: change exception handling
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resize image in the fastest way with no regard of quality and no additional dependencies to meet needs of fallback
     * method.
     *
     * @param magnitude     how much the image size is to be reduced.
     * @param originalImage array of bytes that represent an image.
     */


    private byte[] getFile(ID domainId, String S3DomainName, String FileUrl) {
        return getS3Service().getObject(
                getBuckets().getDomain(),
                "%s/%s/%s/%s".formatted(getMEDIA_TYPE(), S3DomainName, domainId, FileUrl)
        );
    }

}
