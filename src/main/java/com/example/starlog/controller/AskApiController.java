package com.example.starlog.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import com.example.starlog.client.AiClient;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
public class AskApiController {

    private final AiClient aiClient;

    public record AskForm(String question) {}

    @PostMapping("/api/ask")
    public ResponseEntity<?> ask(@RequestBody AskForm form,
                                 @SessionAttribute (name = "loginUserId", required = false) Long loginUserId) {
        if (loginUserId == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요해요."));
        }

        String question = form.question();

        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "질문을 입력해 주세요."));
        }

        question = question.trim();

        if (question.length() > 50) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "질문은 50자까지 입력할 수 있어요."));
        }

        try {
            String answer = aiClient.ask(question); 
            return ResponseEntity.ok(Map.of("answer", answer));
        } catch (HttpServerErrorException.ServiceUnavailable e) {
            return ResponseEntity.status(503)
                    .body(Map.of("message", "지금 AI 사용자가 많아요. 잠시 후 다시 시도해 주세요."));
        } catch (RestClientException e) {
            return ResponseEntity.status(502)
                    .body(Map.of("message", "답변을 불러오지 못했어요. 잠시 후 다시 시도해 주세요."));
        }
    }
}