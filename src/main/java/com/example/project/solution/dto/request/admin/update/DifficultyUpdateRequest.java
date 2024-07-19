package com.example.project.solution.dto.request.admin.update;

import com.example.project.solution.entity.Difficulty;
import lombok.Data;

@Data
public class DifficultyUpdateRequest {
    private String adminId;
    private Long solutionId;
    private Difficulty difficulty;
}
