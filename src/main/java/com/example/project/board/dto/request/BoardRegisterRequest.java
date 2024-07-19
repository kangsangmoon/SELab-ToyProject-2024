package com.example.project.board.dto.request;

import lombok.Data;

@Data
public class BoardRegisterRequest {
    private String title;
    private String context;
    private String userId;
    private Long solutionId;
}
