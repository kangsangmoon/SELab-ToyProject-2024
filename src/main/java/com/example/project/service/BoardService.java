package com.example.project.service;

import com.example.project.dto.board.BoardResponse;
import com.example.project.dto.board.request.*;
import com.example.project.entity.Board;
import com.example.project.exception.BoardException;
import com.example.project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse postRegistration(BoardRegisterRequest request) {
        Board board = new Board(
                request.getTitle(),
                request.getContext(),
                request.getUserId(),
                request.getSolutionId()
        );


        Board savedBoard = boardRepository.save(board);

        return BoardResponse.from(savedBoard);
    }


    @Transactional
    public BoardResponse update(BoardUpdateRequest request) {

        Board board = boardRepository
                .findById(request.getId())
                .orElseThrow(BoardException::new);

        if (board.getId().equals(request.getUserId())) {
            board.updateBoard(request.getTitle(), request.getContext());
            return BoardResponse.from(board);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public BoardResponse read(BoardReadRequest request) {

        Board board = boardRepository
                .findById(request.getBoardId())
                .orElseThrow(BoardException::new);


        return BoardResponse.from(board);
    }


    @Transactional
    public Page<BoardResponse> readAll(BoardReadAllRequest request) {

        return boardRepository
                .findBySolutionId(request.getSolutionId())
                .map(BoardResponse::from);
    }

    @Transactional
    public boolean delete(BoardDeleteRequest request) {
        Board board = boardRepository
                .findById(request.getUserId())
                .orElseThrow(BoardException::new);

        if (board.equals(request.getUserId())) {
            boardRepository.delete(board);
            return true;
        }

        return false;
    }
}