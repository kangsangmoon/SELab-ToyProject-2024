package com.example.project.exception;

import org.springframework.stereotype.Component;

public class RegisterException extends RuntimeException{
    public RegisterException() {
        super("Register Error");
    }
}
