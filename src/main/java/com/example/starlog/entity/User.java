package com.example.starlog.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="users")
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class User {
    @Id                                                   // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // NOT NULL UNIQUE VARCHAR(50)
    private String username;

    @Column(nullable = false)                             // 암호화된 값이라 길이 넉넉히
    private String password;

    @Column(nullable = false, length = 30)
    private String nickname;

    private LocalDateTime createdAt;                      // created_at 컬럼이 됨

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = LocalDateTime.now();
    }
}
