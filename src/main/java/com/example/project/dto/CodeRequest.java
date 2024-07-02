//클라이언트로부터 코드 실행 요청을 받을 DTO
package com.example.project.dto;

public class CodeRequest {
    private String language;
    private String code;
    private String input;
    private String expectedOutput;


    // Getter and Setter methods
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}