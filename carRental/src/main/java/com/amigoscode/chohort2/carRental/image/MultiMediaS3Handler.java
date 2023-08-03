package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;

import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;


/**
 * @param <ID> the type of domain's id
 * @author DmitriKonnovNN
 * @since 0.0.1
 */
public interface MultiMediaS3Handler<ID> {
    String OBJECT_DELIMITER = "/";
    String BUCKET_HYPHEN_DELIMITER = "-";


    /**
     * Check, whether given media type name is appropriate;
     *
     * @see BiPredicate
     *
     * */
    BiPredicate<MediaType, List<String>> isDomain = (mediaType, possibleNames) -> {
        String name = mediaType.getName();
        return possibleNames.contains(name);
    };

    List<String> getPossibleNames();

    List<String>getPossibleMimeTypes();

    S3Service getS3Service();

    int getResizeMagnitude();

    String getMEDIA_TYPE();

    S3Buckets getS3Bucket();

    byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException;

/**
 * S3 Object first level business domain and its MediaType prefix:
 * e.g.: /cars/images/
 * */
    class S3ObjectDomain {
        private String name;
        private List<MediaType> mediaType;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<MediaType> getMediaType() {
            return mediaType;
        }

        public void setMediaType(List<MediaType> mediaType) {
            this.mediaType = mediaType;
        }
    }
    /**
     * Type of Multi Media Object to store in S3. It might be videos, images etc.
     *
     * * */
    class MediaType {
        private String name;
        private int resizeMagnitude;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResizeMagnitude() {
            return resizeMagnitude;
        }

        public void setResizeMagnitude(int resizeMagnitude) {
            this.resizeMagnitude = resizeMagnitude;
        }
    }


    static String resolveBucketNameWithHyphens(String... args) {
        StringJoiner joiner = new StringJoiner(BUCKET_HYPHEN_DELIMITER);
        return resolve(joiner, args);
    }

    static String resolveObjectName(String... args) {
        StringJoiner joiner = new StringJoiner(OBJECT_DELIMITER);
        return resolve(joiner, args);
    }

    static String generateId() {
        return UUID.randomUUID().toString();
    }

    default MediaType resolveMultiMediaType(List<MediaType> types){
        return types.stream()
                .filter(type-> isDomain.test(type, getPossibleNames()))
                .findFirst()
                .orElseThrow(()->{
                    StringJoiner joiner = new StringJoiner(",");
                    getPossibleNames().forEach(joiner::add);
                    return new RuntimeException("no multi media types %s found".formatted(joiner.toString()));
                });
    }

    default String getFullBucketName() {
        return resolveBucketNameWithHyphens(
                getS3Bucket().getOrgName(),
                getS3Bucket().getAccount(),
                getS3Bucket().getOriginalSuffix());
    }

    default String getFullObjectName(String S3DomainName, ID domainId, String fileUrlOrId) {
        return MultiMediaS3Handler.resolveObjectName(
                S3DomainName,
                getMEDIA_TYPE(),
                domainId.toString(),
                fileUrlOrId);
    }

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

        if (file==null || file.isEmpty()) {
            throw new IllegalStateException("File to be uploaded is empty or not found!");
        }
        if (getPossibleMimeTypes().contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        String domainFileId = generateId();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        try {
            getS3Service().putObject(
                    getFullBucketName(),
                    getFullObjectName(S3DomainName, domainId, domainFileId),
                    file.getBytes(),
                    metadata
            );
        } catch (IOException | SdkException e) {

            //TODO: change exception type
            throw new RuntimeException("failed to upload %S to bucket %S".formatted(S3DomainName, getFullBucketName()), e);
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
            return resizeFile(getFile(domainId, S3DomainName, fileUrl), getResizeMagnitude());

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

    //TODO: add other s3 bucket validation
    private static String resolve(StringJoiner joiner, String... args) {
        Arrays.stream(args).forEach(joiner::add);
        return joiner.toString().toLowerCase();
    }

    private byte[] getFile(ID domainId, String S3DomainName, String fileUrl) {
        try {
        return getS3Service().getObject(
                getFullBucketName(),
                resolveObjectName(S3DomainName, getMEDIA_TYPE(), domainId.toString(), fileUrl)); }
        catch (SdkException e) {
            throw new RuntimeException("failed to download %S from bucket %S".formatted(S3DomainName, getFullBucketName()), e);
        }
    }
}
