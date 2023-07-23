package com.amigoscode.chohort2.carRental.image;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Specification of {@link com.amigoscode.chohort2.carRental.image.MultiMediaS3Handler} for handling images in S3 Bucket.
 *
 * @param <T>  domain the image belongs to
 * @param <ID> id of the domain
 * @author DmitriKonnovNN
 * @since 0.0.1
 */

public interface ImageS3Handler<T, ID> extends MultiMediaS3Handler<ID> {

    default byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException {

        BufferedImage bufferedOriginalImage = ImageIO.read(new ByteArrayInputStream(originalImage));
        int targetHeight = bufferedOriginalImage.getHeight() / magnitude;
        int targetWidth = bufferedOriginalImage.getWidth() / magnitude;
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(bufferedOriginalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Transactional
    default String uploadImage(ID domainId, S3ObjectDomain s3ObjectDomain, MultipartFile file) {
        String s3DomainName = s3ObjectDomain.getName();
        return uploadFile(domainId, s3DomainName, file);
    }


    @Override
    default String getMEDIA_TYPE() {
        return getImagesDomain().getName();
    }

    @Override
    default int getResizeMagnitude() {
        return getImagesDomain().getResizeMagnitude();
    }

    default byte[] getOriginalImage(ID domainId, S3ObjectDomain s3ObjectDomain, String fileUrl) {
        String s3DomainName = s3ObjectDomain.getName();
        return getOriginalUnresizedFile(domainId, s3DomainName, fileUrl);
    }

    default byte[] getResizedImage(ID domainId, S3ObjectDomain s3ObjectDomain, String fileUrl) {
        String s3DomainName = s3ObjectDomain.getName();
        return getResizedFile(domainId, s3DomainName, fileUrl);
    }

    /**
     * Upload an image to S3.
     *
     * @param domainId id of a domain that the image to be saved belongs to
     * @param file     image to be saved;
     * @return Domain (Entity) the image that was successfully saved belongs to;
     * @throws RuntimeException - if uploading fails;
     */
    T uploadImage(ID domainId, MultipartFile file);

    /**
     * Retrieve the image directly from S3 with the original resolution. Calling this method from public endpoint causes
     * a lot amount of egress traffic and subsequent charges. Use with caution.
     *
     * @param domainId id of a domain that the image to be retrieved belongs to
     * @return array of bytes;
     */
    byte[] getOriginalImage(ID domainId);

    /**
     * Retrieve the image directly from S3 and resize it to save the egress traffic. The method is intended to be used
     * as a fallback if S3 offloading and/or cloud delivery network (Cloudflare, AWS Cloudformation) fails or
     * the triggered store-postprocessing hasn't finished by the time the file is requested.
     *
     * @param domainId id of a domain that the image to be retrieved belongs to
     * @return array of bytes;
     */
    byte[] getResizedImage(ID domainId);

    /**
     * get name of the domain to be saved. E.g. cars, users etc.
     */

    S3ObjectDomain getS3FileDomain();

    default S3ObjectDomain getS3FileDomain(String domainName) {
        return getS3Bucket()
                .getS3domains()
                .stream()
                .filter(domain -> domain.getName().equals(domainName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("domain %s doesn't exist".formatted(domainName)));
    }

    default MediaType getImagesDomain(S3ObjectDomain fileDomain) {
        return resolveMultiMediaType(
                fileDomain.getMediaType());
    }

    default MediaType getImagesDomain() {
        return getImagesDomain(getS3FileDomain());

    }

    /**
     * get url or string representation of image id (e.g. from DB);
     */

    String getImageUrlOrThrow(T domain);
    @Override
    default List<String> getPossibleNames(){
        return new ArrayList<>(Arrays.asList("images", "pictures", "pics"));
    }

}
