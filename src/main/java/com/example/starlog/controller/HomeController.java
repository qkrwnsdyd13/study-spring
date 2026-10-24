package com.example.starlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginNickname", required = false)String nickname, Model model) {
        model.addAttribute("nickname", nickname);
        return "home";
    }
}