package com.example.project.service;

import com.example.project.ProjectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ProjectApplication.class)
public class JavaCodeExecutorServiceTest {

    @Autowired
    private JavaCodeExecutorService codeExecutionService;

    @Test
    public void testExecuteJavaCode_success() {
        String javaCode = "public class TempJavaCode { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";
        String result = codeExecutionService.executeJavaCode(javaCode);
        assertTrue(result.contains("Hello, World!"));
        assertTrue(result.contains("Exited with error code : 0"));
    }

    @Test
    public void testExecuteJavaCode_compilationError() {
        String javaCode = "public class TempJavaCode { public static void main(String[] args) { System.out.println(Hello, World!); } }";
        String result = codeExecutionService.executeJavaCode(javaCode);
        assertTrue(result.contains("Compilation Error:"));
    }

    @Test
    public void testExecuteJavaCode_runtimeError() {
        String javaCode = "public class TempJavaCode { public static void main(String[] args) { int a = 1 / 0; } }";
        String result = codeExecutionService.executeJavaCode(javaCode);
        assertTrue(result.contains("Exception in thread \"main\" java.lang.ArithmeticException: / by zero"));
    }
}
