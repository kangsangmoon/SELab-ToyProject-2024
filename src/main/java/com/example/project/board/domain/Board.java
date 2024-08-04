package com.example.project.board.domain;

import com.example.project.board.dto.BoardResponse;
import com.example.project.common.BaseEntity;
import com.example.project.restrictions.DomainRestrictions;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseEntity implements DomainRestrictions<BoardResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String context;

    @Column
    private String userId;

    @Column
    private Long solutionId;

    @Builder
    public Board(String title, String context, String userId, Long solutionId) {
        this.title = title;
        this.context = context;
        this.userId = userId;
        this.solutionId = solutionId;
    }

    public void updateBoard(String title, String context){
        this.title = title;
        this.context = context;
    }

    @Override
    public BoardResponse toResponseDto(){
        return BoardResponse.builder()
                .id(id)
                .title(title)
                .context(context)
                .userId(userId)
                .solutionId(solutionId)
                .build();
    }
}
