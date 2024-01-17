package org.articlesblog.dto;

import lombok.Getter;

import java.util.List;

// dto ответа от гигачата
@Getter
public class ChatResponse {
    private List<Choice> choices;

    public ChatResponse(List<Choice> choices) {
        this.choices = choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Getter
    public static class Choice {
        private int index;
        private Message message;

        public Choice(int index, Message message) {
            this.index = index;
            this.message = message;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }
}
