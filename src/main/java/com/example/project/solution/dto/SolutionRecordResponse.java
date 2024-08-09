package com.example.project.solution.dto;

import com.example.project.restrictions.ResponseDto;
import com.example.project.solution.domain.SolutionRecord;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolutionRecordResponse implements ResponseDto<SolutionRecord> {
    private Long id;
    private Long userId;
    private Long solutionId;
    private String code;
    private Boolean successOrNot;

    @Override
    public SolutionRecord toEntity() {
        return new SolutionRecord(
                id,
                userId,
                solutionId,
                code,
                successOrNot
        );
    }
}
