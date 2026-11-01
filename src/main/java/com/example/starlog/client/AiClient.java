package com.example.starlog.client;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


/*Repository와 비슷한 역할
Controller    요청 받기
    ↓
Service       업무 로직
    ↓              ↓
Repository     Client
(DB에 접근)    (외부 서버에 접근)
    ↓              ↓
 Neon DB        FastAPI*/

@Component 
public class AiClient {
    private final RestClient restClient;
    private final String internalKey;
    
    public AiClient(@Value("${ai.server-url}") String serverUrl, 
                    @Value("${ai.internal-key}") String internalKey){
        this.restClient = RestClient.create(serverUrl);
        this.internalKey = internalKey;
    }

    public record FortuneResponse(String content) {}

    public String fortune(LocalDate birthDate){
        FortuneResponse response = restClient.post()
                .uri("/fortune")
                .header("X-Internal-Key", internalKey)
                .body(Map.of("birth_date", birthDate.toString()))
                .retrieve()
                .body(FortuneResponse.class);

        return response.content();
    }

    
}
