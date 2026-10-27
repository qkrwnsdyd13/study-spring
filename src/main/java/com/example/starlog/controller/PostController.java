package com.example.starlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.starlog.dto.PostForm;
import com.example.starlog.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/write")
    public String writeForm(@SessionAttribute(name = "loginUserId", required = false) Long userId,
                            Model model) {
        if (userId == null) {
            return "redirect:/auth/login";        // 로그인 안 했으면 로그인 화면으로
        }
        model.addAttribute("form", new PostForm());
        return "post-write";
    }

    @PostMapping("/write")
    public String write(@SessionAttribute(name = "loginUserId", required = false) Long userId,
                        @Valid @ModelAttribute("form") PostForm form,
                        BindingResult bindingResult) {
        if (userId == null) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "post-write";
        }
        Long postId = postService.write(form, userId);
        return "redirect:/posts/" + postId;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post-list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("post", postService.viewPost(id));
        return "post-detail";
    }
}