package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @param <ID> the type of domain's id
 * @author DmitriKonnovNN
 * @since 0.0.1
 */
public interface MultiMediaS3Handler<ID> {


    S3Buckets getBuckets();

    S3Service getS3Service();


    String getMEDIA_TYPE();

    byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException;

    /**
     * Upload a file to S3.
     *
     * @param domainId     id of a domain that the file to be saved belongs to
     * @param S3DomainName name of the domain to be saved. E.g. cars, users etc.
     * @param file         multipart file to be saved;
     * @return string representation of id or url (identification) of the uploaded file;
     * @throws RuntimeException - if uploading fails;
     */
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

    /**
     * Retrieve the file directly from S3 and resize it to save the egress traffic. The method is intended to be used
     * as a fallback if S3 offloading and/or cloud delivery network (Cloudflare, AWS Cloudformation) fails or
     * the triggered store-postprocessing hasn't finished by the time the file is requested.
     *
     * @param domainId     id of a domain that the file to be retrieved belongs to
     * @param S3DomainName domain name that the file to be retrieved belongs to
     * @param fileUrl      url or string representation of id
     */

    default byte[] getResizedFile(ID domainId, String S3DomainName, String fileUrl) {
        try {
            return resizeFile(getFile(domainId, S3DomainName, fileUrl), getS3Service().getCarResizeMagnitude());

            //TODO: change exception handling
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve the file directly from S3 with the original resolution. Calling this method from public endpoint causes
     * a lot amount of egress traffic and subsequent charges. Use with caution.
     *
     * @param domainId     id of a domain that the file to be retrieved belongs to
     * @param S3DomainName domain name that the file to be retrieved belongs to
     * @param fileUrl      url or string representation of id
     */

    default byte[] getOriginalUnresizedFile(ID domainId, String S3DomainName, String fileUrl) {

        return getFile(domainId, S3DomainName, fileUrl);
    }

    private byte[] getFile(ID domainId, String S3DomainName, String FileUrl) {
        return getS3Service().getObject(
                getBuckets().getDomain(),
                "%s/%s/%s/%s".formatted(getMEDIA_TYPE(), S3DomainName, domainId, FileUrl)
        );
    }
}
