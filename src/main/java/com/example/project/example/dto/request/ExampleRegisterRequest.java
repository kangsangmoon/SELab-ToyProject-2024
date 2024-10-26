package com.example.project.example.dto.request;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.example.domain.Example;
import lombok.Data;

@Data
public class ExampleRegisterRequest implements RegisterRequest<Example> {
    private String inExample;
    private String outExample;
    private Long solutionId;

    @Override
    public Example toEntity() {
        return new Example(
                this.inExample,
                this.outExample,
                this.solutionId
        );
    }
}