package com.example.project.solution.dto.request.user.filter_find;

import com.example.project.solution.entity.Difficulty;
import lombok.Data;

@Data
public class DifficultyFindRequest {
    private Difficulty difficulty;
}
