package com.example.project.example.repository;

import com.example.project.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExampleRepository  extends JpaRepository<Example, Long>, JpaSpecificationExecutor<Example> {
    Optional<Example> findBySolutionId(Long solutionId, Long id);

    List<Example> findAllBySolutionId(Long solutionId);
}