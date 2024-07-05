package com.example.project.dto.board.request;

import lombok.Data;

@Data
public class BoardRegisterRequest {
    private Long solutionId;
    private Long userId;
    private String title;
    private String context;
}
