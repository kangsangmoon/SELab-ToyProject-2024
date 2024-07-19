package com.example.project.board.dto.request;

import lombok.Data;

@Data
public class BoardDeleteRequest {
    private Long userId;
    private Long boardId;
}
