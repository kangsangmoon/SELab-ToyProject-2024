package com.example.project.comment.repository;

import com.example.project.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByBoardIdOrderByCreatedAt(Long boardId);
}
