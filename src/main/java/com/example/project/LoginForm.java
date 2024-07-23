package com.example.project;


import lombok.Data;

@Data
public class LoginForm {
    public static void login(String[] args) {
        LoginForm loginForm = new LoginForm();
    }

    private String userId;
    private String password;

}
