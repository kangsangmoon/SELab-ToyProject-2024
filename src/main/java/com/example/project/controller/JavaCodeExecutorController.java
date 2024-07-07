package com.example.project.controller;

import com.example.project.service.JavaCodeExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/execute")
public class JavaCodeExecutorController {

    @Autowired
    private JavaCodeExecutorService executionService;

    @PostMapping
    public String executeJavaCode(@RequestBody String javaCode) {
        return executionService.executeJavaCode(javaCode);
    }
}