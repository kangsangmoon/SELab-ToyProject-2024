package com.example.project.board.controller;

import com.example.project.board.common.dto.PageDto;
import com.example.project.board.common.dto.ResponseDto;
import com.example.project.board.dto.request.*;
import com.example.project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody BoardRegisterRequest request) {
        var response = boardService.postRegistration(request);

        return ResponseDto.created(response);
    }

    @GetMapping
    public ResponseEntity<?> read(@RequestBody BoardReadRequest request) {
        var response = boardService.read(request);

        return ResponseDto.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        var response = boardService.readAll(pageable);

        return ResponseDto.ok(response);
    }

    /*@GetMapping("/solution")
    public ResponseEntity<?> readSolutionBoard(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            BoardReadAllRequest request
    ) {
        var response = boardService.readSolutionBoard(pageable, request);

        return PageDto.ok(response);
    }*/


    @PutMapping
    public ResponseEntity<?> update(BoardUpdateRequest request) {
        var response = boardService.update(request);

        return ResponseDto.ok(response);
    }


    @DeleteMapping
    public ResponseEntity<Void> delete(BoardDeleteRequest request) {
        boardService.delete(request);

        return ResponseDto.noContent();
    }
}
