package com.example.starlog.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.starlog.dto.RegisterForm;
import com.example.starlog.entity.User;
import com.example.starlog.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterForm form){
        if(userRepository.existsByUsername(form.getUsername())){
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다");
        }
        String encoded = passwordEncoder.encode(form.getPassword());
        userRepository.save(new User(form.getUsername(), encoded, form.getNickname()));
    }
}
