package main.java.com.example.storage.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.example.storage.service.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final S3StorageService s3StorageService;

    @Autowired
    public StorageController(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFiles(
            @RequestParam String userName,
            @RequestParam String searchTerm) {
        List<String> files = s3StorageService.searchFiles(userName, searchTerm);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam String userName,
            @RequestParam String fileName) throws IOException {
        S3Object s3Object = s3StorageService.downloadFile(userName, fileName);
        byte[] content = s3Object.getObjectContent().readAllBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam String userName,
            @RequestParam String fileName,
            @RequestParam("file") MultipartFile file) throws IOException {
        s3StorageService.uploadFile(userName, fileName, file.getInputStream());
        return ResponseEntity.ok("File uploaded successfully.");
    }
}
