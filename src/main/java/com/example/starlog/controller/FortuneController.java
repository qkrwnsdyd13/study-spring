package com.example.starlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class FortuneController {
    
    @GetMapping("/fortune")
    public String fortune(@SessionAttribute(name = "loginUserId", required = false) Long userId) {
        if (userId == null) {
            return "redirect:/auth/login";
        }
        return "fortune";      // 운세 계산 없이 화면만 바로
    }
}
