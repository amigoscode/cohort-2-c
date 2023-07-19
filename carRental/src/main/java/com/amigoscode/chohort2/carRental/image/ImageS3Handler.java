package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageS3Handler<T, ID> extends MultiMediaS3Handler<T, ID> {

    private final String MEDIA_TYPE;

    public ImageS3Handler(@Autowired S3Service s3Service,
                          @Autowired S3Buckets s3Buckets,
                          @Value(value = "images") String mediaType) {
        super(s3Service,s3Buckets);
        this.MEDIA_TYPE = mediaType;

    }


    @Override
    protected int getResizeMagnitude() {
        return s3Service.getCarResizeMagnitude();
    }

    protected byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException {

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
    public String uploadImage(ID domainId, String S3DomainName, MultipartFile file) {
        return uploadFile(domainId, S3DomainName, file);
    }

    @Override
    protected String getMEDIA_TYPE() {
        return this.MEDIA_TYPE;
    }

    public byte[] getOriginalImage(ID domainId, String S3DomainName, String fileUrl) {

        return getOriginalUnresizedFile(domainId, S3DomainName, fileUrl);
    }

    public byte[] getResizedImage(ID domainId, String S3DomainName, String fileUrl) {
        return getResizedFile(domainId, S3DomainName, fileUrl);
    }
}
