package org.articlesblog.controllers;

import org.articlesblog.dto.gigachatdto.ChatRequest;
import org.articlesblog.dto.gigachatdto.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatController {

    @Qualifier("chatRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    // версия модели чата
    @Value("${chat.model}")
    private String model;

    // адрес отправки сообщения
    @Value("@{chat.api.url}")
    private String apiUrl;

    // отправка запроса чату с просьбой генерации сообщения
    @PostMapping("/chat/completions")
    public String chat(@RequestParam String prompt){
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()){
            return "No response";
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
}