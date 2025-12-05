package com.lyhorng.pedssystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lyhorng.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("CUS-404", ex.getMessage(), "មិនមានអតិថិជននេះទេ"));
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<?> handleDuplicateField(DuplicateFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("CUS-400", ex.getMessage(), "ទិន្នន័យមានរួចហើយ"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("SYS-500", ex.getMessage(), "បញ្ហាពីប្រព័ន្ធ"));
    }
}