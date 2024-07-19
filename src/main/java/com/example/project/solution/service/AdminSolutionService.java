package com.example.project.solution.service;

import com.example.project.solution.dto.SolutionResponse;
import com.example.project.solution.dto.request.admin.DeleteRequest;
import com.example.project.solution.dto.request.admin.RegisterRequest;
import com.example.project.solution.dto.request.admin.update.ContextUpdateRequest;
import com.example.project.solution.dto.request.admin.update.DifficultyUpdateRequest;
import com.example.project.solution.dto.request.admin.update.ExampleUpdateRequest;
import com.example.project.solution.dto.request.admin.update.UpdateRequest;
import com.example.project.solution.entity.Solution;
import com.example.project.solution.exception.SolutionException;
import com.example.project.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSolutionService {
    private SolutionRepository solutionRepository;

    @Transactional
    public SolutionResponse register(RegisterRequest request) {
        Solution solution = new Solution(
                request.getDifficulty(),
                request.getTitle(),
                request.getDescription(),
                request.getInExample(),
                request.getOutExample(),
                0L
        );

        Solution savedSolution = solutionRepository.save(solution);

        return SolutionResponse.from(savedSolution);
    }

    @Transactional
    public boolean delete(DeleteRequest request) {
        if (request.getAdminId().equals("admin")) {
            if (solutionRepository.existsById(request.getSolutionId())) {
                solutionRepository.deleteById(request.getSolutionId());
                return true;
            }
        }
        return false;
    }

    private Solution findBySolutionId(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionException::new);
    }

    private boolean isAdmin(String id) {
        return id.equals("admin");
    }

    @Transactional
    public SolutionResponse contextUpdate(ContextUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = findBySolutionId(request.getSolutionId());

            solution.updateContext(request.getTitle(), request.getDescription());

            return SolutionResponse.from(solution);
        }

        return null;
    }

    @Transactional
    public SolutionResponse difficultyUpdate(DifficultyUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = findBySolutionId(request.getSolutionId());

            solution.updateDifficulty(request.getDifficulty());

            return SolutionResponse.from(solution);
        }
        return null;
    }

    @Transactional
    public SolutionResponse exampleUpdate(ExampleUpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = findBySolutionId(request.getSolutionId());

            solution.updateExample(request.getInExample(), request.getOutExample());

            return SolutionResponse.from(solution);
        }
        return null;
    }

    @Transactional
    public SolutionResponse updateAll(UpdateRequest request) {
        if (isAdmin(request.getAdminId())) {
            Solution solution = findBySolutionId(request.getSolutionId());

            solution.update(
                    request.getDifficulty(),
                    request.getTitle(),
                    request.getDescription(),
                    request.getInExample(),
                    request.getOutExample()
            );

            return SolutionResponse.from(solution);
        }
        return null;
    }
}
