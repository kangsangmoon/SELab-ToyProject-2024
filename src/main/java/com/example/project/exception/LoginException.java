package com.example.project.exception;

import org.springframework.stereotype.Component;

public class LoginException extends RuntimeException{
    public LoginException() {
        super("Login Error");
    }

    public LoginException(String message) {
        super(message);
    }
}
