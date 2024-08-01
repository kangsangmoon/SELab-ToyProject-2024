package com.example.project.board.dto.request;

import com.example.project.board.domain.Board;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardRegisterRequest {
    @NotNull
    private String title;
    @NotNull
    private String context;
    @NotNull
    private String userId;
    @NotNull
    private Long solutionId;

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .userId(userId)
                .context(context)
                .solutionId(solutionId)
                .build();
    }
}
