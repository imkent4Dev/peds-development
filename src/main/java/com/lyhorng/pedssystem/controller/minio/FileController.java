package com.lyhorng.pedssystem.controller.minio;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileController {

    private final MinioClient minioClient;
    private static final String BUCKET = "test-bucket";
    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5MB

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        try {
            var buckets = minioClient.listBuckets();
            return ResponseEntity.ok(buckets);
        } catch (Exception e) {
            log.error("Health check failed", e);
            return ResponseEntity.status(500).body("MinIO Error: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Empty file");
            }

            long size = file.getSize();
            if (size > MAX_SIZE) {
                return ResponseEntity.status(413)
                        .body("File too large. Max = 5MB. Your file = " + (size / (1024 * 1024.0)) + "MB");
            }

            String original = file.getOriginalFilename();
            String ext = original != null && original.contains(".")
                    ? original.substring(original.lastIndexOf("."))
                    : "";

            // Folder yyyyMMdd
            String folder = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());

            // Filename yyyyMMddHHmmss
            String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());

            String filePath = folder + "/" + timestamp + ext;

            // Upload
            InputStream input = file.getInputStream();

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(BUCKET)
                    .object(filePath)
                    .stream(input, size, -1)
                    .contentType(file.getContentType())
                    .build()
            );

            return ResponseEntity.ok(new UploadResponse(
                    "Upload successful",
                    original,
                    folder,
                    timestamp + ext,
                    filePath,
                    size,
                    file.getContentType()
            ));

        } catch (Exception e) {
            log.error("Upload failed", e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            String folder = "";

            // detect folder yyyyMMdd
            if (filename.length() >= 8 && filename.substring(0, 8).matches("\\d+")) {
                folder = filename.substring(0, 8);
            }

            String path = folder.isEmpty()
                    ? filename
                    : folder + "/" + filename;

            // Try first path
            InputStream stream;
            try {
                stream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(BUCKET)
                        .object(path)
                        .build());
            } catch (Exception e) {
                // fallback: try root
                if (!path.equals(filename)) {
                    stream = minioClient.getObject(GetObjectArgs.builder()
                            .bucket(BUCKET)
                            .object(filename)
                            .build());
                } else {
                    throw e;
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(stream);

        } catch (Exception e) {
            log.error("Get file failed", e);
            return ResponseEntity.status(404).body("File not found: " + filename);
        }
    }

    public record UploadResponse(
            String message,
            String originalFilename,
            String folder,
            String filename,
            String fullPath,
            long size,
            String contentType
    ) {}
}