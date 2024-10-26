package com.example.project.solution.dto.response;

import com.example.project.restrictions.ResponseDto;
import com.example.project.solution.domain.Solution;
import com.example.project.solution.domain.vo.Difficulty;
import lombok.Builder;
import lombok.Data;

@Data
public class SolutionResponse implements ResponseDto<Solution> {
    private Long id;
    private Difficulty difficulty;
    private String title;
    private String description;
    private Long solved;

    @Builder
    public SolutionResponse(Long id, Difficulty difficulty, String title, String description, Long solved) {
        this.id = id;
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.solved = solved;
    }

    @Override
    public Solution toEntity() {
        return new Solution(
                this.difficulty,
                this.title,
                this.description,
                this.solved
        );
    }
}