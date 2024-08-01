package com.example.project.board.dto;

import com.example.project.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private String context;
    private String userId;
    private Long solutionId;


    public static BoardResponse from(Board board){
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContext(),
                board.getUserId(),
                board.getSolutionId()
        );
    }
}