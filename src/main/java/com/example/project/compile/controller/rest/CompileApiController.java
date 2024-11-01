package com.example.project.compile.controller.rest;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.common.dto.ResponseDto;
import com.example.project.common.dto.ResponseMessage;
import com.example.project.common.util.HeaderUtil;
import com.example.project.compile.dto.CompileRequest;
import com.example.project.compile.service.CompileService;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.dto.ErrorResponseDto;
import com.example.project.example.domain.Example;
import com.example.project.example.service.ExampleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompileApiController {

    private final CompileService compileService;
    private final AuthTokenService authTokenService;
    private final ExampleService exampleService;

    @PostMapping("/compile")
    public ResponseEntity<?> compileCode(@RequestBody CompileRequest request, HttpServletRequest httpServletRequest) throws IOException {
        if (authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            Long exampleId = request.getExampleId();
            Example example = exampleService.getExampleById(exampleId);

            String result = compileService.compileAndRun(
                    request.getLanguage(),
                    request.getCode(),
                    example.getInExample()
            );

            if (result.equals(example.getOutExample())) {
                return ResponseDto.toResponseEntity(ResponseMessage.CORRECT_ANSWER, result);
            }
            return ResponseDto.toResponseEntity(ResponseMessage.WRONG_ANSWER, result);
        }
        return ErrorResponseDto.of(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER);
    }
}