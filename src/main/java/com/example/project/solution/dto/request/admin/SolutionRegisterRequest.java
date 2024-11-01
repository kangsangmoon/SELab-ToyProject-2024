package com.example.project.solution.dto.request.admin;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.Solution;
import lombok.Data;

@Data
public class SolutionRegisterRequest implements RegisterRequest<Solution> {
    private Difficulty difficulty;
    private String title;
    private String description;

    @Override
    public Solution toEntity() {
        return new Solution(
                this.difficulty,
                this.title,
                this.description,
                0L
        );
    }
}