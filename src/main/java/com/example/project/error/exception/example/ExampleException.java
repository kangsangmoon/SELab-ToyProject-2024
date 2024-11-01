package com.example.project.error.exception.example;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class ExampleException extends BusinessException {
    public ExampleException() {super(ErrorMessage.EXAMPLE_NOT_FOUND_ERROR);}
}
