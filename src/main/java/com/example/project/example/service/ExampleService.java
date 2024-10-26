package com.example.project.example.service;

import com.example.project.error.exception.example.ExampleException;
import com.example.project.error.exception.example.ExampleNotFindByIdException;
import com.example.project.example.domain.Example;
import com.example.project.example.domain.VariableTypeSelect;
import com.example.project.example.dto.request.ExampleDeleteRequest;
import com.example.project.example.dto.request.ExampleRegisterRequest;
import com.example.project.example.dto.response.ExampleResponse;
import com.example.project.example.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleRepository exampleRepository;
    private final VariableTypeSelect variableTypeSelect;

    @Transactional
    public ExampleResponse exampleRegister(ExampleRegisterRequest request) {
        return exampleRepository
                .save(request.toEntity())
                .toResponseDto();
    }

    @Transactional
    public ExampleResponse exampleDelete(ExampleDeleteRequest request) {
        Example example = exampleRepository.findById(request.getExampleId())
                .orElseThrow(ExampleNotFindByIdException::new);

        exampleRepository.delete(example);

        return example.toResponseDto();
    }

    @Transactional(readOnly = true)
    public ExampleResponse read(Long id, Long exampleId) {
        return exampleRepository
                .findBySolutionId(id, exampleId)
                .orElseThrow(ExampleException::new)
                .toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<ExampleResponse> readAll(Long solutionId) {
        return exampleRepository.findAllBySolutionId(solutionId).stream()
                .map(Example::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<Object> convertExamples(List<String> examples) {
        return examples.stream()
                .map(variableTypeSelect::convertExample)
                .collect(Collectors.toList());
    }
}