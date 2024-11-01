package com.example.project.compile.service;

import com.example.project.compile.domain.CompileLanguage;
import com.example.project.error.dto.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final String codeDir;

    public FileService(@Value("${compile.url}") String codeDir) {
        this.codeDir = codeDir;
    }

    public Path createCodeFile(String code, CompileLanguage compileLanguage) throws IOException {
        try {
            Path codePath = Paths.get(codeDir);
            if (!Files.exists(codePath)) {
                Files.createDirectories(codePath);
            }

            String filename = "Main_" + UUID.randomUUID() + compileLanguage.getExtension();

            Path filePath = codePath.resolve(filename);
            Files.writeString(filePath, code);
            return filePath;

        } catch (SecurityException e) {
            throw new IOException(ErrorMessage.ACCESS_PERMISSION_EXCEPTION.getMessage(), e);
        }
    }
}