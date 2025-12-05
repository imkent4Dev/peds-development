package com.lyhorng.pedssystem.common;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private Status status;
    private T data;
    private LocalDateTime timestamp;

    @Data
    @Builder
    public static class Status {
        private String code;
        private String message;
        private String khMessage;
    }

    public static <T> ApiResponse<T> success(String code, String message, String khMessage, T data) {
        return ApiResponse.<T>builder()
                .status(Status.builder()
                        .code(code)
                        .message(message)
                        .khMessage(khMessage)
                        .build())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String code, String message, String khMessage) {
        return ApiResponse.<T>builder()
                .status(Status.builder()
                        .code(code)
                        .message(message)
                        .khMessage(khMessage)
                        .build())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
