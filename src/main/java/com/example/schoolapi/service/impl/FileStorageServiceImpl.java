package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.FileRecordDao;
import com.example.schoolapi.entity.FileRecord;
import com.example.schoolapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final S3Client s3Client;
    private final FileRecordDao fileRecordDao;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;

    @Value("${cloudflare.r2.public-url}")
    private String publicUrl;

    public FileStorageServiceImpl(S3Client s3Client, FileRecordDao fileRecordDao) {
        this.s3Client = s3Client;
        this.fileRecordDao = fileRecordDao;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Generate a unique file name to avoid overwriting
            String extension = getFileExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + (extension.isEmpty() ? "" : "." + extension);

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putOb, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Tạo URL public
            String fullUrl = publicUrl + "/" + fileName;

            // Lưu metadata vào Database (FileRecord)
            FileRecord record = new FileRecord();
            record.setName(file.getOriginalFilename());
            record.setPath(fullUrl);
            record.setOwner("system"); // Tạm để system, sau này có login thì set tên user
            fileRecordDao.save(record);

            return fullUrl;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Cloudflare R2", e);
        }
    }

    @Override
    public void deleteFile(String fileKey) {
        // Xoá mềm trong Database thay vì tác động vật lý lên R2
        String searchPath = fileKey;
        if (!searchPath.startsWith("http")) {
            searchPath = publicUrl + "/" + fileKey;
        }

        // Tìm record trong DB và cập nhật trạng thái
        fileRecordDao.findByPath(searchPath).ifPresent(record -> {
            record.setDeleted(true);
            record.setActive(false);
            fileRecordDao.save(record);
        });

        // Ghi chú: Ta không gọi lệnh S3Client copy/delete nữa, 
        // file vật lý vẫn nằm nguyên trên Cloudflare R2 để làm minh chứng lịch sử.
    }

    @Override
    public byte[] downloadFile(String fileKey) {
        if (fileKey.startsWith(publicUrl)) {
            fileKey = fileKey.substring(publicUrl.length() + 1);
        }

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return objectBytes.asByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tải file: " + fileKey, e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
