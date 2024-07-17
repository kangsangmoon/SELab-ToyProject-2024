package com.example.project;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class User {
    @Id
    private final String id;
    private final String password;
}
