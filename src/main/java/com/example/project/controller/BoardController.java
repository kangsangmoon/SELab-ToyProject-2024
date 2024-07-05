package com.example.project.controller;

import com.example.project.common.ResponseDto;
import com.example.project.dto.board.request.BoardRegisterRequest;
import com.example.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseDto<?> register(BoardRegisterRequest request){
        var response = boardService.register(request);

        return ResponseDto.created(response);
    }
}
