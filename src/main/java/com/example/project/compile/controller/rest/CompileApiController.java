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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompileApiController {

    private final CompileService compileService;
    private final AuthTokenService authTokenService;
    private final ExampleService exampleService;

    @PostMapping("/{solutionId}")
    public ResponseEntity<?> compileCode(
            @PathVariable Long solutionId,
            @RequestBody CompileRequest request,
            HttpServletRequest httpServletRequest) throws IOException, InterruptedException {

        if (!authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            return ErrorResponseDto.of(ErrorMessage.NOT_FOUND_CLIENT_ID_HEADER);
        }

        Example example = exampleService.getExampleBySolutionId(solutionId);
        if (example == null) {
            log.warn("문제에 맞는 예제를 찾지 못함: {}", solutionId);
            return ErrorResponseDto.of(ErrorMessage.EXAMPLE_NOT_FOUND_ERROR);
        }

        log.info("문제에 맞는 예제를 찾음: {}, inExample: {}, outExample: {}",
                solutionId, example.getInExample(), example.getOutExample());

        List<Object> parsedInputs = example.getInExample() != null && !example.getInExample().isEmpty()
                ? exampleService.parseInExample(example.getInExample())
                : List.of();

        String result;
        try {
            result = compileService.sendCodeToCompileServer(
                    request.getLanguage(),
                    request.getCode(),
                    parsedInputs,
                    example.getOutExample()
            );
        } catch (IOException | InterruptedException e) {
            log.error("컴파일 중 오류 발생: {}", e.getMessage(), e);
            return ErrorResponseDto.of(ErrorMessage.EXECUTION_FAILED);
        }

        if (result.equals(ErrorMessage.GENERAL_COMPILE_ERROR.getMessage())) {
            log.warn("컴파일 실패: {}", result);
            return ErrorResponseDto.of(ErrorMessage.EXECUTION_FAILED);
        }

        log.info("컴파일 성공: {}", result);
        return ResponseDto.toResponseEntity(ResponseMessage.COMPILE_SUCCESS, result);
    }
}