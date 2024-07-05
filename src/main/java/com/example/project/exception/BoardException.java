package com.example.project.exception;

public class BoardException extends BusinessException{
    public BoardException() {
        super(ErrorMessage.USER_NOT_FOUND_ERROR);
    }
}