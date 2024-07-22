package com.example.project.exception;

public class BoardException extends BusinessException{
    public BoardException() {
        super(ErrorMessage.BOARD_NOT_FOUND_ERROR);
    }
}
