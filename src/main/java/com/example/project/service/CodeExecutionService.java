package com.example.project.service;

import com.example.project.dao.*;
import com.example.project.dto.CodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.stream.Collectors;

@Service
public class CodeExecutionService {

    @Autowired
    private CodeResultDAO codeResultDAO;

    public CodeResult executeScript(String language, String code, String input) throws IOException, InterruptedException {
        // 임시 파일에 코드 저장
        File tempFile = File.createTempFile("temp", "." + getFileExtension(language));
        FileWriter writer = new FileWriter(tempFile);
        writer.write(code);
        writer.close();

        // 컴파일 및 실행 명령어 생성
        ProcessBuilder builder;
        if (language.equalsIgnoreCase("java")) {
            // 자바 코드 컴파일
            builder = new ProcessBuilder("javac", tempFile.getAbsolutePath());
            Process compileProcess = builder.start();
            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode != 0) {
                throw new RuntimeException("Java compile error: " + new BufferedReader(new InputStreamReader(compileProcess.getErrorStream())).lines().collect(Collectors.joining("\n")));
            }

            // 자바 코드 실행
            String className = tempFile.getAbsolutePath().replace(".java", "");
            builder = new ProcessBuilder("java", "-cp", tempFile.getParent(), className.substring(className.lastIndexOf(File.separator) + 1));
        } else if (language.equalsIgnoreCase("python")) {
            // 파이썬 코드 실행
            builder = new ProcessBuilder("python3", tempFile.getAbsolutePath());
        } else if (language.equalsIgnoreCase("c")) {
            // C 코드 컴파일
            String outputFileName = tempFile.getAbsolutePath() + ".out";
            builder = new ProcessBuilder("gcc", tempFile.getAbsolutePath(), "-o", outputFileName);
            Process compileProcess = builder.start();
            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode != 0) {
                throw new RuntimeException("C compile error: " + new BufferedReader(new InputStreamReader(compileProcess.getErrorStream())).lines().collect(Collectors.joining("\n")));
            }

            // C 코드 실행
            builder = new ProcessBuilder(outputFileName);
        } else {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }

        // 입력 데이터 전달
        Process process = builder.start();
        if (input != null) {
            OutputStream os = process.getOutputStream();
            os.write(input.getBytes());
            os.close();
        }

        // 실행 결과 캡처
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        reader.close();
        process.waitFor();

        CodeResult codeResult = new CodeResult();
        codeResult.setOutput(output.toString().trim());
        codeResult.setCorrect(false);

        return codeResultDAO.save(codeResult);
    }

    private String getFileExtension(String language) {
        switch (language.toLowerCase()) {
            case "java":
                return "java";
            case "python":
                return "py";
            case "c":
                return "c";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}