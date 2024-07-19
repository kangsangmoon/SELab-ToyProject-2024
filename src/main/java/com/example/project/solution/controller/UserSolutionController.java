package com.example.project.solution.controller;

import com.example.project.board.common.dto.PageDto;
import com.example.project.solution.dto.request.user.CompileRequest;
import com.example.project.solution.dto.request.user.FindRequest;
import com.example.project.solution.dto.request.user.filter_find.DifficultyFindRequest;
import com.example.project.solution.service.UserSolutionService;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/solution/user")
@RequiredArgsConstructor
public class UserSolutionController {

    private final UserSolutionService service;

    @GetMapping("/difficulty")
    public ResponseEntity<?> findByDifficulty
            (
                    @RequestBody DifficultyFindRequest request,
                    @PageableDefault(sort = "solved", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        var byDifficulty = service.findByDifficulty(request);

        return PageDto.ok(byDifficulty);
    }

    @PostMapping
    public ResponseEntity<?> compile(CompileRequest request){
        return null;
    }

    @GetMapping
    public ResponseEntity<?> findSolution(FindRequest request){
        var response = service.findSolution(request);

        return ResponseEntity.ok(response);
    }
}
