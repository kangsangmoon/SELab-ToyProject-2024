package com.example.project.compile.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum CompileLanguage {
    C(".c", "gcc %s -o %s"),
    CPP(".cpp", "g++ %s -o %s"),
    JAVA(".java", "javac -encoding UTF-8 %s"),
    PYTHON(".py", "python -X utf8 %s"),
    JAVASCRIPT(".js", "node %s");

    private final String extension;
    private final String compileCommand;

    private static final Map<String, CompileLanguage> LANGUAGE_MAP = new HashMap<>();

    static {
        LANGUAGE_MAP.put("c", C);
        LANGUAGE_MAP.put("cpp", CPP);
        LANGUAGE_MAP.put("java", JAVA);
        LANGUAGE_MAP.put("python", PYTHON);
        LANGUAGE_MAP.put("js", JAVASCRIPT);
    }

    public static CompileLanguage getLanguage(String language) {
        return LANGUAGE_MAP.get(language.toLowerCase());
    }
}