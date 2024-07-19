package com.example.project.board.repository;

import com.example.project.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    public Page<Board> findBySolutionId(Long solutionId);
}
