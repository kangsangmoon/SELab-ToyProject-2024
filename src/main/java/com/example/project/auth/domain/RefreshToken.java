package com.example.project.auth.domain;

import com.example.project.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RefreshToken extends BaseEntity {

    @Id
    private String refreshToken;
    private Long userId;
}
