package com.example.starlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller 
public class ChatController {

    @GetMapping("/chat")
    public String chat(@SessionAttribute(name = "loginUserId", required = false) Long loginUserId) {
        if (loginUserId == null) {
            return "redirect:/auth/login";
        }
        return "chat";
    }
}