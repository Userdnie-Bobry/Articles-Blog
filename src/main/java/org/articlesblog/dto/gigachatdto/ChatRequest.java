package org.articlesblog.dto.gigachatdto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// dto запроса к гигачату
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

    public void setModel(String model) {
        this.model = model;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
