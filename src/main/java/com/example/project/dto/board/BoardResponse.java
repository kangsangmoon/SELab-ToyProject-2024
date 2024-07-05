package com.example.project.dto.board;

import lombok.Data;

@Data
public class BoardResponse {
    private Long boardId;
    private Long writerUserId;
    private String title;
    private String context;
}