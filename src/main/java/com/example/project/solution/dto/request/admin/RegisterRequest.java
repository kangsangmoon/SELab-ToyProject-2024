package com.example.project.solution.dto.request.admin;

import com.example.project.solution.entity.Difficulty;
import lombok.Data;

@Data
public class RegisterRequest {
    private String adminId;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
}
