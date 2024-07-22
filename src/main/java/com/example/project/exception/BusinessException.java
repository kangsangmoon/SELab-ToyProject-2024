package com.example.project.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage message) {
        super(message.getDeclaringClass().descriptorString());
        this.errorMessage = message;
    }

    public BusinessException(ErrorMessage message, String reason) {
        super(reason);
        this.errorMessage = message;
    }

    public BusinessException(String reason) {
        super(reason);
        this.errorMessage = ErrorMessage.CONFLICT_ERROR;
    }
}