package com.example.project.solution.dto;

import com.example.project.solution.domain.Difficulty;
import com.example.project.solution.domain.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class SolutionResponse {
    private Long id;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
    private Long solved;

    public static SolutionResponse from(Solution solution) {
        return new SolutionResponse(
                solution.getId(),
                solution.getDifficulty(),
                solution.getTitle(),
                solution.getDescription(),
                solution.getInExample(),
                solution.getOutExample(),
                solution.getSolved()
        );
    }
    @Builder
    public SolutionResponse(Long id, Difficulty difficulty, String title, String description, String inExample, String outExample, Long solved) {
        this.id = id;
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.inExample = inExample;
        this.outExample = outExample;
        this.solved = solved;
    }
}
