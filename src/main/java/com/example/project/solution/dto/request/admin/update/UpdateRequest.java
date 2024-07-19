package com.example.project.solution.dto.request.admin.update;

import com.example.project.solution.entity.Difficulty;
import lombok.Data;

@Data
public class UpdateRequest {
    private String adminId;
    private Long solutionId;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
}
