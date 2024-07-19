package com.example.project.solution.dto.request.admin.update;

import lombok.Data;

@Data
public class ContextUpdateRequest {
    private String adminId;
    private Long solutionId;
    private String title;
    private String description;
}
