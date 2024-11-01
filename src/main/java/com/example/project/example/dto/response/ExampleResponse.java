package com.example.project.example.dto.response;

import com.example.project.restrictions.ResponseDto;
import com.example.project.example.domain.Example;
import lombok.Builder;
import lombok.Data;

@Data
public class ExampleResponse implements ResponseDto<Example> {
    private Long id;
    private String inputExample;
    private String outputExample;
    private Long solutionId;

    @Builder
    public ExampleResponse(Long id, String inputExample, String outputExample) {
        this.id = id;
        this.inputExample = inputExample;
        this.outputExample = outputExample;
    }

    @Override
    public Example toEntity() {
        return new Example(
                this.inputExample,
                this.outputExample,
                this.solutionId
        );
    }
}