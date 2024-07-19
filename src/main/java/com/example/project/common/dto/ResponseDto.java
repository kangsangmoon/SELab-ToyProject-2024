package com.example.project.common.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@RequiredArgsConstructor
public class ResponseDto<T> {
    private final T data;

    public static <T> ResponseEntity<ResponseDto<T>> ok(T data) {
        var response = new ResponseDto<T>(data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ResponseDto<T>> created(T data) {
        var response = new ResponseDto<T>(data);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}