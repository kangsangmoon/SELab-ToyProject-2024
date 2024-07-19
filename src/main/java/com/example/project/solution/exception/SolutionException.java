package com.example.project.solution.exception;

public class SolutionException extends BusinessException {
    public SolutionException() {
        super(ErrorMessage.SOLUTION_NOT_FOUND_ERROR);
    }
}