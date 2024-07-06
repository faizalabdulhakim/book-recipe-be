package com.faizal.book.recipe.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket-name}")
    private String minioBucketName;

    public String uploadFile(MultipartFile file, String fileName) {
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioBucketName)
                            .object(fileName).stream(is, is.available(), -1)
                            .contentType(file.getContentType())
                            .build());

            return minioUrl + "/" + minioBucketName + "/" + fileName;
        } catch (Exception e) {
            log.error("Error upload file to Minio", e);
            return null;
        }
    }
}
