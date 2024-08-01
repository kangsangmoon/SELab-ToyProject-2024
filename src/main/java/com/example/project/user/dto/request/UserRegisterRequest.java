package com.example.project.user.dto.request;

import com.example.project.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String email;


    public User toEntity(){
        return User.builder()
                .email(email)
                .userId(userId)
                .password(password)
                .name(name)
                .build();
    }
}
