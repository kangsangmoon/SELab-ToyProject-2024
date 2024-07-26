package com.example.project.dto;

import lombok.Data;

@Data
public class AddUserRequest {
    private String userId;
    private String password;
    private String repeatPassword;
    private String userName;
    private String email;
}
