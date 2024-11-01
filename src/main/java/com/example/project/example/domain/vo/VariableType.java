package com.example.project.example.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum VariableType {
    INTEGER("-?\\d+", Integer::parseInt),
    DOUBLE("-?\\d*\\.\\d+", Double::parseDouble),
    BOOLEAN("true|false", Boolean::parseBoolean, Pattern.CASE_INSENSITIVE),
    CHAR("^.$", value -> {
        if (value.length() == 1) {
            return value.charAt(0);
        } else {
            throw new IllegalArgumentException("Invalid character input");
        }
    }),
    STRING(".*", value -> value);

    private final String pattern;
    private final Converter converter;

    VariableType(String regex, Converter converter, int flags) {
        this.pattern = String.valueOf(Pattern.compile(regex, flags));
        this.converter = converter;
    }

    public boolean matches(String value) {
        return Pattern.matches(pattern,value);
    }

    public Object convert(String value) {
        return converter.convert(value);
    }

    @FunctionalInterface
    private interface Converter {
        Object convert(String value);
    }
}