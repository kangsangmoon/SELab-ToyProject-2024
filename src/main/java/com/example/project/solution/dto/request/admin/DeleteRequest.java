package com.example.project.solution.dto.request.admin;

import lombok.Data;

@Data
public class DeleteRequest {
    private String adminId;
    private Long solutionId;
}
