package com.example.schoolapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    
    /**
     * Uploads a file to Cloudflare R2 and returns its public URL or unique key.
     */
    String uploadFile(MultipartFile file);
    
    /**
     * Deletes a file from Cloudflare R2 by its unique key.
     */
    void deleteFile(String fileKey);

    /**
     * Downloads a file from Cloudflare R2 as a byte array.
     */
    byte[] downloadFile(String fileKey);
}
