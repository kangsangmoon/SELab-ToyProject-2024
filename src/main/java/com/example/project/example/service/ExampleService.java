package com.example.project.example.service;

import com.example.project.error.exception.example.ExampleNotFindByIdException;
import com.example.project.example.domain.Example;
import com.example.project.example.domain.ExampleParser;
import com.example.project.example.dto.request.ExampleDeleteRequest;
import com.example.project.example.dto.request.ExampleRegisterRequest;
import com.example.project.example.dto.response.ExampleResponse;
import com.example.project.example.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleRepository exampleRepository;

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

    public Example getExampleBySolutionId(Long solutionId) {
        return exampleRepository.findBySolutionId(solutionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 솔루션 ID에 대한 예제를 찾을 수 없습니다."));
    }

    public List<Object> parseInExample(String inExample) {
        return ExampleParser.parseInExample(inExample);  // ExampleParser 사용
    }
}