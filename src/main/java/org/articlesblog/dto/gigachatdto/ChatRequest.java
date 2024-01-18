package org.articlesblog.dto.gigachatdto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// dto запроса к гигачату
@Setter
@Getter
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
