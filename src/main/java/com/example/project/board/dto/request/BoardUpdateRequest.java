package com.example.project.board.dto.request;

import lombok.Data;

@Data
public class BoardUpdateRequest {
    private Long id;
    private Long userId;
    private String title;
    private String context;
}
