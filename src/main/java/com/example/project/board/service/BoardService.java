package com.example.project.board.service;

import com.example.project.board.dto.BoardResponse;
import com.example.project.board.dto.request.*;
import com.example.project.board.entity.Board;
import com.example.project.board.repository.BoardRepository;
import com.example.project.exception.BoardException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    @Transactional
    public BoardResponse postRegistration(BoardRegisterRequest request) {
        Board board = new Board(
                request.getTitle(),
                request.getContext(),
                request.getUserId(),
                request.getSolutionId()
        );


        Board savedBoard = repository.save(board);

        return BoardResponse.from(savedBoard);
    }


    @Transactional
    public BoardResponse update(BoardUpdateRequest request) {

        Board board = repository
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

        Board board = repository
                .findById(request.getBoardId())
                .orElseThrow(BoardException::new);


        return BoardResponse.from(board);
    }


    @Transactional(readOnly = true)
    public Page<BoardResponse> readAll(Pageable pageable) {

        return repository.findAll(pageable).map(BoardResponse::from);
    }

    /*@Transactional(readOnly = true)
    public Page<BoardResponse> readSolutionBoard(Pageable pageable, BoardReadAllRequest request){
        return repository
                .findBySolutionId(request.getSolutionId())
                .map(BoardResponse::from);
    }*/

    @Transactional
    public boolean delete(BoardDeleteRequest request) {
        Board board = repository
                .findById(request.getUserId())
                .orElseThrow(BoardException::new);

        if (board.equals(request.getUserId())) {
            repository.delete(board);
            return true;
        }

        return false;
    }
}