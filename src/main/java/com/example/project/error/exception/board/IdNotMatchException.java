package com.example.project.error.exception.board;

import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.BusinessException;

public class IdNotMatchException extends BusinessException {
    public IdNotMatchException(ErrorMessage message) {
        super(message);
    }

    public IdNotMatchException(ErrorMessage message, String reason) {
        super(message, reason);
    }

    public IdNotMatchException(String reason) {
        super(reason);
    }
}
