package org.articlesblog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatRestTemplateConfig {

    @Value("${chat.api.key}")
    private String chatKey;

    // авторизация запросов
    @Bean
    @Qualifier("chatRestTemplate")
    public RestTemplate chatRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Content-Type", "application/json");
            request.getHeaders().add("Authorization", "Bearer " + chatKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}