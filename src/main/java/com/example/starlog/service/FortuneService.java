package com.example.starlog.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.starlog.client.AiClient;
import com.example.starlog.entity.Fortune;
import com.example.starlog.entity.User;
import com.example.starlog.repository.FortuneRepository;
import com.example.starlog.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FortuneService {
    private final UserRepository userRepository;
    private final FortuneRepository fortuneRepository;
    private final AiClient aiClient;

    @Transactional
    public String getTodayFortune(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        
        if (user.getBirthDate() == null) {
            throw new IllegalStateException("생년월일을 등록하면 운세를 볼 수 있어요");
        }

        LocalDate today = LocalDate.now();
        return fortuneRepository.findByUserAndFortuneDate(user, today)
        .map(Fortune::getContent)
        .orElseGet(() -> {                              // 없으면 AI 호출 후 저장
                    String content = aiClient.fortune(user.getBirthDate());
                    fortuneRepository.save(new Fortune(user, today, content));
                    return content;
                });
    }
}
