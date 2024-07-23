package com.example.project.service;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class CCodeExecutorServiceTest {

    private final CCodeExecutorService cCodeExecutorService = new CCodeExecutorService();

    @Test
    void testExecuteCCodeSuccess() throws IOException, InterruptedException {
        String cCode = "#include <stdio.h>\nint main() { printf(\"Hello, World!\\n\"); return 0; }";
        String output = cCodeExecutorService.executeCCode(cCode);
        assertEquals("Hello, World!\n", output);
    }

    @Test
    void testExecuteCCodeCompilationError() throws IOException, InterruptedException {
        String cCode = "#include <stdio.h>\nint main() { printf(\"Hello, World!\\n\" return 0; }";  // Missing semicolon
        String output = cCodeExecutorService.executeCCode(cCode);
        assertTrue(output.contains("Compilation Error:"));
    }
}
