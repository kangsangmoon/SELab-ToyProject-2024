package com.example.project.controller;

import com.example.project.dto.CodeRequest;
import com.example.project.dto.CodeResult;
import com.example.project.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class CodeExecutionController {


    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping("/execute")
    public CodeResult executeCode(@RequestBody CodeRequest codeRequest) {
        String language = codeRequest.getLanguage();
        String code = codeRequest.getCode();
        String input = codeRequest.getInput();
        String expectedOutput = codeRequest.getExpectedOutput();

        CodeResult codeResult;
        try {
            codeResult = codeExecutionService.executeScript(language, code, input);
            codeResult.setCorrect(codeResult.getOutput().trim().equals(expectedOutput.trim()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            codeResult = new CodeResult();
            codeResult.setOutput("Error executing code: " + e.getMessage());
            codeResult.setCorrect(false);
        }
        return codeResult;
    }
}