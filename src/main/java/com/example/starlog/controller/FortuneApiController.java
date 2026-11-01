package com.example.starlog.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestClientException;

import com.example.starlog.service.FortuneService;

import lombok.RequiredArgsConstructor;


@RestController 
@RequiredArgsConstructor 
public class FortuneApiController {

    private final FortuneService fortuneService;

    @GetMapping("/api/fortune")
    public ResponseEntity<Map<String, String>> fortune(@SessionAttribute(name = "loginUserId", required = false) Long userId,
                          Model model) {

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인이 필요해요"));           // 로그인 안 했으면 로그인으로
        }
        try {
            return ResponseEntity.ok(Map.of("content", fortuneService.getTodayFortune(userId)));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (RestClientException e) {
            System.out.println("AI 서버 호출 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", "지금은 운세를 불러올 수 없어요. 잠시 후 다시 시도해 주세요."));
        }
    }
    
}
