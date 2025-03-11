package com.kasyus.product_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String uploadFile(MultipartFile file) throws Exception;
    void deleteFile(String fileName) throws Exception;
    void initBucket() throws Exception;
}