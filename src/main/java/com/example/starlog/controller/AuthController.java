package com.example.starlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.starlog.dto.RegisterForm;
import com.example.starlog.entity.User;
import com.example.starlog.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterForm form,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.register(form);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "duplicate", e.getMessage());
            return "register";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session, Model model) {
        try {
            User user = userService.login(username, password);              // ① Service에 로그인 맡기기

            session.setAttribute("loginUserId", user.getId());    // ② 회원 id 저장
            session.setAttribute("loginNickname", user.getNickname());  // ③ 닉네임 저장

            return "redirect:/";                           // ④ 성공하면 홈으로
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());     // ⑤ 에러 메시지 담기
            return "login";                           // ⑥ 로그인 화면 다시
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    

}