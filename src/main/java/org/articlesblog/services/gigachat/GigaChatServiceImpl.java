package org.articlesblog.services.gigachat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.feignclients.gigachat.GigaChatAuthFeignClient;
import org.articlesblog.feignclients.gigachat.GigaChatGenFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GigaChatServiceImpl implements GigaChatService {

    @Value("${gigachat.clientSecret}")
    private String clientSecret;

    @Value("${gigachat.clientId}")
    private String clientId;

    @Value("${gigachat.scope}")
    private String scope;

    @Value("${gigachat.model}")
    private String model;

    private final GigaChatAuthFeignClient gigaChatAuthFeignClient;
    private final GigaChatGenFeignClient gigaChatGenFeignClient;

    public String getAccessToken() {
        String authorization = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
        log.info("auth data: " + authorization);
        String rqUID = UUID.randomUUID().toString();
        String body = "scope=" + scope;

        String response = gigaChatAuthFeignClient.getAccessToken("Basic " + authorization, rqUID, body);

        return response.substring(response.indexOf("access_token=") + 18, response.length() - 29);
    }

    public String getModel() {
        String token = getAccessToken();
        log.info("token: " + token);

        String response = gigaChatGenFeignClient.getModel("Bearer " + token);

        int startIndex = response.indexOf(model);
        if (startIndex != -1) {
            model = response.substring(startIndex, startIndex + model.length());
        }
        else {
            log.info("Модель устарела. Выберите новую");
        }

        return model;
    }

    public String getAnswer(String content) {
        String token = getAccessToken();
        String model = getModel();
        log.info("model: " + model);

        String jsonPayload = String.format("""
            {
              "model": "%s",
              "messages": [
                {
                  "role": "system",
                  "content": "Перескажи этот текст своими словами, сохраняя его основной смысл и структуру."
                },
                {
                  "role": "user",
                  "content": "%s"
                }
              ],
              "temperature": 0.7
            }""", model, content);

        String response = gigaChatGenFeignClient.getAnswer("Bearer " + token, jsonPayload);

        return response.substring(response.indexOf("choices") + 33, response.length() - 224);
    }
}