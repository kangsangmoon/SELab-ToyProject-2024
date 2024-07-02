//코드 실행 결과를 나타내는 DTO
package com.example.project.dto;

import lombok.Data;

@Data
public class CodeResult {
    private Long id;
    private String output;
    private boolean isCorrect;
}