package com.example.project.solution.dto.request.user.filter_find;

import com.example.project.solution.domain.Difficulty;
import lombok.Data;

@Data
public class SolutionDifficultyFindRequest {
    private Difficulty difficulty;
}
