package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @param <T> the type of domain owning image
 * @param <ID> the type of domain's id
 *
 * */

public abstract class ImageHandler<T, ID> {

    abstract protected void setImgUrl(T domain, String imgUrl);

    abstract protected String getImgUrl(T domain);

    abstract protected S3Buckets getBuckets();

    abstract protected S3Service getS3Service();
    abstract protected String getS3DomainName();

    /**
     * Upload an image to S3.
     * @param domainId id of object to be saved
     * @param domain object that an image to be saved belongs to
     * @param file object to be saved;
     * @throws RuntimeException
     * */
    @Transactional
    public void uploadImage(ID domainId, T domain, MultipartFile file) {

        String domainImageId = UUID.randomUUID().toString();
        try {
            getS3Service().putObject(
                    getBuckets().getDomain(),
                    "images/%S/%s/%s".formatted(getS3DomainName(),domainId, domainImageId),
                    file.getBytes()
            );
        } catch (IOException e) {

            //TODO: change exception
            throw new RuntimeException("failed to upload profile image", e);
        }
        setImgUrl(domain, domainImageId);

    }

    /**
     * Retrieve the image directly from S3 with the original resolution. Calling this method from public endpoint causes
     * a lot amount of egress traffic and subsequent charges. Use with caution.
     * @param domainId id of object to be saved
     * @param domain object that an image to be saved belongs to
     * */

    public byte[] getOriginalUnresizedImage(ID domainId, T domain) {

        return getImage(domainId, domain);
    }


    /**
     * Retrieve the image directly from S3 and resize it to save the egress traffic. The method is intended to be used
     * as a fallback if S3 offloading and/or cloud delivery network (Cloudflare, AWS Cloudformation) fails or
     * the triggered store-postprocessing hasn't finished by the time the image is requested.
     * @param domainId id of object to be saved
     * @param domain object that an image to be saved belongs to
     * */
    public byte[] getOriginalResizedImage(ID domainId, T domain) {
        try {
            return resizeImage(getImage(domainId, domain), getS3Service().getCarResizeMagnitude());

            //TODO: change exception handling
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resize image in the fastest way with no regard of quality and no additional dependencies to meet needs of fallback
     * method.
     * @param magnitude how much the image size is to be reduced.
     * @param originalImage array of bytes that represent an image.
     * */
    private byte[] resizeImage(byte[] originalImage, int magnitude) throws IOException {
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

    private byte[] getImage(ID domainId, T domain) {

        if (StringUtils.isBlank(getImgUrl(domain))) {
            throw new ApiRequestException(ErrorConstants.IMAGE_NOT_FOUND, "no image exists");
        }

        return getS3Service().getObject(
                getBuckets().getDomain(),
                "images/%s/%s/%s".formatted(getS3DomainName(),domainId, getImgUrl(domain))
        );
    }

}
