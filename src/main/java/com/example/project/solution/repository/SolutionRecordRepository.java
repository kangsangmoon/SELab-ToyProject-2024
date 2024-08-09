package com.example.project.solution.repository;

import com.example.project.solution.domain.SolutionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRecordRepository extends JpaRepository<SolutionRecord, Long> {
    List<SolutionRecord> findByUserId(Long userId);
    List<SolutionRecord> findByUserIdAndSolutionId(Long userId, Long solutionId);
}
