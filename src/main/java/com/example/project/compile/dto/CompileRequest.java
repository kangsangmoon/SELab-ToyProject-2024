package com.example.project.compile.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class CompileRequest {
    private String language;
    private String code;

    @Setter
    @Getter
    private List<String> examples;

}