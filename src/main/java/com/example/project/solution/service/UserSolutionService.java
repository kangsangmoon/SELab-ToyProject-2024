package com.example.project.solution.service;

import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.user.FindRequest;
import com.example.project.solution.dto.request.user.filter_find.DifficultyFindRequest;
import com.example.project.solution.entity.Solution;
import com.example.project.solution.exception.SolutionException;
import com.example.project.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSolutionService {

    private final SolutionRepository solutionRepository;

    @Transactional(readOnly = true)
    public Page<SolutionResponse> findAll(Pageable pageable) {
        return solutionRepository
                .findAll(pageable)
                .map(SolutionResponse::from);
    }

    @Transactional(readOnly = true)
    public SolutionResponse findSolution(FindRequest request) {
        Solution solution = solutionRepository.findById(request.getSolutionId()).orElseThrow(SolutionException::new);

        return SolutionResponse.from(solution);
    }

    @Transactional(readOnly = true)
    public Page<SolutionResponse> findByDifficulty(DifficultyFindRequest request){
        return solutionRepository
                .findByDifficulty(request.getDifficulty())
                .map(SolutionResponse::from);
    }

    //TODO 컴파일 부분 만들기
}
