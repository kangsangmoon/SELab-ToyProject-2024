package com.example.project.example.domain;

import org.springframework.stereotype.Component;
import com.example.project.example.domain.vo.VariableType;

@Component
public class VariableTypeSelect {
    public VariableType selectVariableType(String value) {
        for (VariableType type : VariableType.values()) {
            if (type.matches(value)) {
                    return type;
            }
        }
        return VariableType.STRING;
    }

    public Object convertExample(String value) {
        VariableType type = selectVariableType(value);
        return type.convert(value);
    }
}