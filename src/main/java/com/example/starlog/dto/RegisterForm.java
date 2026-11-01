package com.example.starlog.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterForm {
    @NotBlank(message = "아이디를 입력하세요")
    @Size(min = 4, max = 20, message = "아이디는 4~20자입니다")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 4, message = "비밀번호는 4자 이상입니다")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;
    @Past(message = "생년월일은 오늘 이전이어야 합니다")
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;   // @NotNull 안 붙임 → 선택 입력
}