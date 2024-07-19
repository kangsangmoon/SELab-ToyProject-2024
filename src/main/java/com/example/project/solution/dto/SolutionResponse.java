package com.example.project.solution.dto;

import com.example.project.solution.entity.Difficulty;
import com.example.project.solution.entity.Solution;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolutionResponse {
    private Long id;
    private Difficulty difficulty;
    private String title;
    private String description;
    private String inExample;
    private String outExample;
    private Long solved;

    public static SolutionResponse from(Solution solution){
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
}
