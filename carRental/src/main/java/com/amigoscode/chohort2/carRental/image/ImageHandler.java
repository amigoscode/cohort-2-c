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

    @Transactional
    public void uploadImage(ID domainId, T domain, MultipartFile file) {

        String domainImageId = UUID.randomUUID().toString();
        try {
            getS3Service().putObject(
                    getBuckets().getDomain(),
                    "images/%s/%s".formatted(domainId, domainImageId),
                    file.getBytes()
            );
        } catch (IOException e) {

            //TODO: change exception
            throw new RuntimeException("failed to upload profile image", e);
        }
        setImgUrl(domain, domainImageId);

    }

    public byte[] getOriginalUnresizedImage(ID domainId, T domain) {

        return getImage(domainId, domain);
    }


    public byte[] getOriginalResizedImage(ID domainId, T domain) {
        try {
            return resizeImage(getImage(domainId, domain), getS3Service().getCarResizeMagnitude());

            //TODO: change exception handling
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
                "images/%s/%s".formatted(domainId, getImgUrl(domain))
        );
    }

}
