//클라이언트로부터 코드 실행 요청을 받을 DTO
package com.example.project.dto;

import lombok.Data;

@Data
public class CodeRequest {
    private String language;
    private String code;
    private String input;
    private String expectedOutput;

}