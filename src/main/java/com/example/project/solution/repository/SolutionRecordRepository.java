package com.example.project.solution.repository;

import com.example.project.solution.domain.SolutionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRecordRepository extends JpaRepository<SolutionRecord, Long> {
}
