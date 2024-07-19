package com.example.project.board.entity;

import com.example.project.board.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String context;
    private String userId;
    private Long solutionId;

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
}
