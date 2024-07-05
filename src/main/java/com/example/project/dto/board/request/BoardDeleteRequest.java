package com.example.project.dto.board.request;

import lombok.Data;

@Data
public class BoardDeleteRequest {
    private Long userId;
    private Long boardId;
}
