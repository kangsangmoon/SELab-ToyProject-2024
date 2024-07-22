package com.example.project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity // JPA 엔티티 사용하기 위해
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함해 생성자 추가

public class User {
    @Id
    private String id;
    private String password;
}
