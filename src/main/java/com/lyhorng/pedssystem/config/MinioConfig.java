package com.lyhorng.pedssystem.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    private static final String MINIO_URL = "http://localhost:9000";    // internal
    private static final String ACCESS_KEY = "admin";
    private static final String SECRET_KEY = "admin12345";

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(MINIO_URL)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
    }
}