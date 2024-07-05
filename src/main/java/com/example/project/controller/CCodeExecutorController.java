package com.example.project.controller;

import com.example.project.service.CCodeExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController("/api/ccode")
public class CCodeExecutorController {

    @Autowired
    private CCodeExecutorService cCodeExecutorService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeCCode(@RequestBody String cCode) {
        try {
            String output = cCodeExecutorService.executeCCode(cCode);
            return ResponseEntity.ok(output);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error executing C code: " + e.getMessage());
        }
    }
}
