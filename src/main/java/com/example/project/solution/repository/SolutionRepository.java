package com.example.project.solution.repository;

import com.example.project.solution.entity.Difficulty;
import com.example.project.solution.entity.Solution;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    //Page<Solution> findByDifficulty(Difficulty difficulty);
}
