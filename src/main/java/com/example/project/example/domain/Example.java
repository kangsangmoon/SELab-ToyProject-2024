package com.example.project.example.domain;

import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import com.example.project.example.dto.response.ExampleResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Example extends BaseEntity implements Domain<ExampleResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inExample;

    private String outExample;

    private Long solutionId;

    public Example(String inExample, String outExample, Long solutionId) {
        this.inExample = inExample;
        this.outExample = outExample;
        this.solutionId = solutionId;
    }

    @Override
    public ExampleResponse toResponseDto() {
        return ExampleResponse.builder()
                .id(id)
                .inputExample(inExample)
                .outputExample(outExample)
                .build();
    }
}