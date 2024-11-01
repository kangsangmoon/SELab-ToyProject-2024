package com.example.project.compile.service;

import com.example.project.compile.domain.CompileLanguage;
import com.example.project.error.dto.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompileService {
//TODO 일반적인 컴파일 오류 메시지를 어떤 오류가 발생했는지에 대한 오류메시지 작성
    private final FileService fileService;
    private final CommandExecutorService commandExecutorService;

    @SneakyThrows
    public String compileAndRun(String language, String code, String inputParameter) {
        CompileLanguage compileLanguage = CompileLanguage.getLanguage(language);
        if (compileLanguage == null) {
            return ErrorMessage.UNSUPPORTED_LANGUAGE.getMessage();
        }

        Path filePath = null;
        try {
            filePath = fileService.createCodeFile(code, compileLanguage);
            return executeCode(filePath, inputParameter);
        } catch (InvalidPathException e) {
            return ErrorMessage.INVALID_PATH_EXCEPTION.getMessage();
        } catch (IOException e) {
            return ErrorMessage.GENERAL_COMPILE_ERROR.getMessage();
        } finally {
            if (filePath != null) {
                Files.deleteIfExists(filePath);
            }
        }
    }

    private String executeCode(Path filePath, String inputParameter) throws IOException {
        String result;
        try {
            String command = filePath.getParent().resolve("output") + inputParameter;
            result = commandExecutorService.runCommand(command);
        } catch (IOException | InterruptedException e) {
            return ErrorMessage.GENERAL_COMPILE_ERROR.getMessage();
        }
        return result;
    }
}