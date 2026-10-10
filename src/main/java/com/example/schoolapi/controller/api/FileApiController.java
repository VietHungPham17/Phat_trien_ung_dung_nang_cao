package com.example.schoolapi.controller.api;

import com.example.schoolapi.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileApiController {

    private final FileStorageService fileStorageService;

    public FileApiController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // Danh sách các định dạng được phép
    private static final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "docx", "jpg", "jpeg", "png");

    /**
     * POST /api/files/upload
     * Uploads a file and returns its public URL.
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }

        // Kiểm tra định dạng file
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Định dạng không được hỗ trợ. Chỉ cho phép: pdf, docx, jpg, jpeg, png"));
            }
        }
        
        String fileUrl = fileStorageService.uploadFile(file);
        return ResponseEntity.ok(Map.of("url", fileUrl));
    }

    /**
     * DELETE /api/files
     * Deletes a file by its URL or key.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteFile(@RequestParam("fileKey") String fileKey) {
        fileStorageService.deleteFile(fileKey);
        return ResponseEntity.noContent().build();
    }
    /**
     * GET /api/files/download
     * Tải file trực tiếp về máy thay vì xem trên trình duyệt.
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileKey") String fileKey) {
        byte[] data = fileStorageService.downloadFile(fileKey);
        ByteArrayResource resource = new ByteArrayResource(data);

        // Lấy tên file gốc
        String fileName = "downloaded_file";
        if (fileKey.contains("/")) {
            fileName = fileKey.substring(fileKey.lastIndexOf("/") + 1);
        } else {
            fileName = fileKey;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(data.length)
                .body(resource);
    }
}
