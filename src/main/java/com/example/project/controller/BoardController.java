package com.example.project.controller;

import com.example.project.common.ResponseDto;
import com.example.project.dto.board.request.*;
import com.example.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        var response = boardService.register(request);

        return ResponseDto.created(response);
    }

    @GetMapping
    public ResponseEntity<?> read(@RequestBody BoardReadRequest request) {
        var response = boardService.read(request);

        return ResponseDto.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> readAll(BoardReadAllRequest request) {
        var response = boardService.readAll(request);

        return ResponseDto.ok(response);
    }


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
