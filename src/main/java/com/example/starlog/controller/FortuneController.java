package com.example.starlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestClientException;

import com.example.starlog.service.FortuneService;

import lombok.RequiredArgsConstructor;


@Controller 
@RequiredArgsConstructor 
public class FortuneController {
    private final FortuneService fortuneService;

    @GetMapping("/fortune")
    public String getMethodName(@SessionAttribute(name = "loginUserId", required = false) Long userId,
                          Model model) {

        if (userId == null) {
            return "redirect:/auth/login";               // 로그인 안 했으면 로그인으로
        }
        try {
            model.addAttribute("content", fortuneService.getTodayFortune(userId));
        } catch (IllegalStateException e) {              // 생년월일 없음
            model.addAttribute("message", e.getMessage());
        } catch (RestClientException e) {                // AI 서버가 꺼졌거나 오류
            System.out.println("AI 서버 호출 실패: " + e.getMessage());   // ← 추가
            model.addAttribute("message", "지금은 운세를 불러올 수 없어요. 잠시 후 다시 시도해 주세요.");
        }
        return "fortune";
    }
    
}
