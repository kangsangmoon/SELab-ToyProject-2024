package com.example.project.controller;

import com.example.project.dto.CodeRequest;
import com.example.project.dto.CodeResult;
import com.example.project.service.CodeExecutionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController(value = "/api")
@RequiredArgsConstructor
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @PostMapping("/execute")
    public CodeResult executeCode(@RequestBody CodeRequest codeRequest) {
        log.info("excuteCode 메소드 진입");

        CodeResult codeResult;

        try {
            codeResult = codeExecutionService.executeScript(
                    codeRequest.getLanguage(),
                    codeRequest.getCode(),
                    codeRequest.getInput()
            );

            codeResult.setCorrect(
                    codeResult
                            .getOutput()
                            .trim()
                            .equals(codeRequest.getExpectedOutput().trim())
            );

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            codeResult = new CodeResult();
            codeResult.setOutput("Error executing code: " + e.getMessage());
            codeResult.setCorrect(false);
        }

        return codeResult;
    }
}