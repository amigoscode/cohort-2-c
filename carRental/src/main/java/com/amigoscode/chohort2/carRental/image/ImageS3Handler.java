package com.amigoscode.chohort2.carRental.image;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Specification of {@link com.amigoscode.chohort2.carRental.image.MultiMediaS3Handler} for handling images in S3 Bucket.
 *
 * @param <T>  domain the image belongs to
 * @param <ID> id of the domain
 * @author DmitriKonnovNN
 * @since 0.0.1
 */

public interface ImageS3Handler<T, ID> extends MultiMediaS3Handler<ID> {

    String MEDIA_TYPE = "images";

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
    default String uploadImage(ID domainId, String S3DomainName, MultipartFile file) {
        return uploadFile(domainId, S3DomainName, file);
    }

    @Override
    default String getMEDIA_TYPE() {
        return this.MEDIA_TYPE;
    }

    default byte[] getOriginalImage(ID domainId, String S3DomainName, String fileUrl) {

        return getOriginalUnresizedFile(domainId, S3DomainName, fileUrl);
    }

    default byte[] getResizedImage(ID domainId, String S3DomainName, String fileUrl) {
        return getResizedFile(domainId, S3DomainName, fileUrl);
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
    String getS3FileDomainName();

    /**
     * get url or string representation of image id (e.g. from DB);
     */

    String getImageUrlOrThrow(T domain);


}
