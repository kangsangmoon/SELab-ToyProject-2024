package com.example.project.error.exception;

import com.example.project.error.dto.ErrorMessage;

public class NotExistMemberException extends BusinessException {
    public NotExistMemberException(ErrorMessage message) {
        super(message);
    }

    public NotExistMemberException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public NotExistMemberException(String reason) {
        super(reason);
    }
}