package com.example.project.service;

import com.example.project.dto.CodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.stream.Collectors;

/*
* # 모듛과
* 1. 메소드를 분리하라
*   - 한 메소드 안에 if문이 여러개 들어가지 않도록 해라
*   - 한 메소드 안에 for문이 여러개 들어가지 않도록 해라
*
* */

@Service
public class CodeExecutionService {

    @Autowired
    private CodeResultService codeResultService;


    public CodeResult executeScript(String language, String code, String input) throws IOException, InterruptedException {
        File codeFile = makeCodeFile(language,code);

        ProcessBuilder builder;                                                                                         // 컴파일 및 실행 명령어 생성
        if (language.equalsIgnoreCase("java")) {
            builder = new ProcessBuilder("javac", codeFile.getAbsolutePath());                                // 자바 코드 컴파일

            Process compileProcess = builder.start();
            exception(compileProcess, compileProcess.waitFor(), "java");

            String className = codeFile                                                                                 // 자바 코드 실행
                    .getAbsolutePath()
                    .replace(".java", "");


            builder = new ProcessBuilder(
                    "java",
                    "-cp",
                    codeFile.getParent(),
                    className.substring(className.lastIndexOf(File.separator) + 1)
            );

        } else if (language.equalsIgnoreCase("python")) {                                                    // 파이썬 코드 실행
            builder = new ProcessBuilder("python3", codeFile.getAbsolutePath());
        } else if (language.equalsIgnoreCase("c")) {                                                         // C 코드 컴파일
            String outputFileName = codeFile.getAbsolutePath() + ".out";
            builder = new ProcessBuilder("gcc", codeFile.getAbsolutePath(), "-o", outputFileName);


            Process compileProcess = builder.start();
            exception(compileProcess,  compileProcess.waitFor(), "C");

            builder = new ProcessBuilder(outputFileName);                                                               // C 코드 실행
        } else {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }


        Process process = builder.start();
        matchAnswer(process, input);

        return codeResultService.save(mappingCodeResult(process));
    }

    private File makeCodeFile(String language, String code) throws IOException {
        File codeFile = File.createTempFile("temp", "." + getFileExtension(language));                       // 임시 파일에 코드 저장

        FileWriter writer = new FileWriter(codeFile);
        writer.write(code);
        writer.close();

        return codeFile;
    }

    private String getFileExtension(String language) {
        return switch (language.toLowerCase()) {
            case "java" -> "java";
            case "python" -> "py";
            case "c" -> "c";
            default -> throw new IllegalArgumentException("Unsupported language: " + language);
        };
    }

    private void exception(Process compileProcess,int compileExitCode ,String language) throws RuntimeException{
        if (compileExitCode != 0) {
            throw new RuntimeException(language + " compile error: " +
                    new BufferedReader(
                            new InputStreamReader(compileProcess.getErrorStream())
                    ).lines().collect(Collectors.joining("\n")));
        }
    }

    private void matchAnswer(Process process, String input) throws IOException {         // 입력 데이터 전달
        if (input != null) {
            OutputStream os = process.getOutputStream();
            os.write(input.getBytes());
            os.close();
        }
    }

    private CodeResult mappingCodeResult(Process process) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));                    // 실행 결과 캡처
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

        return codeResult;
    }
}