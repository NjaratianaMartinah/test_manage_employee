package com.adm.test.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse {
    private int code;
    private Object data;
    private String message;
    private LocalDateTime responseDatetime;

    public static <T> ResponseEntity<ApiResponse> buildResponse(T data, HttpStatus status, String message) {
        ApiResponse responseFormatDto = ApiResponse.builder()
                .code(status.value())
                .data(data)
                .message(message)
                .responseDatetime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(responseFormatDto);
    }

    public static <T> ResponseEntity<ApiResponse> buildResponse(T data, HttpStatus status) {
        return buildResponse(data, status, "");
    }
}
