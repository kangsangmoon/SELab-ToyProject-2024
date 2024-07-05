package com.example.project.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExecutionServiceTests {

    private final JavaCodeExecutorService javaCodeExecutorService = new JavaCodeExecutorService();

    @Test
    void testExecuteJavaCodeSuccess() {
        String javaCode = "System.out.println(\"Hello, World!\");";
        String result = javaCodeExecutorService.executeJavaCode(javaCode);
        assertTrue(result.contains("Hello, World!"), "The output should contain 'Hello, World!'");
    }

    @Test
    void testExecuteJavaCodeCompilationError() {
        String javaCode = "System.out.prntln(\"Hello, World!\");";
        String result = javaCodeExecutorService.executeJavaCode(javaCode);
        assertEquals("Compilation error.", result, "The output should indicate a compilation error.");
    }

    @Test
    void testExecuteJavaCodeExecutionError() {
        String javaCode = "int a = 1 / 0;";
        String result = javaCodeExecutorService.executeJavaCode(javaCode);
        assertEquals("Execution error.", result, "The output should indicate an execution error.");
    }
}
