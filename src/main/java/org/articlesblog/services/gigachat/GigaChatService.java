package org.articlesblog.services.gigachat;

import java.io.IOException;

public interface GigaChatService {
    String getAccessToken() throws IOException, InterruptedException;
    String getModel() throws IOException, InterruptedException;
    String getAnswer(String content) throws IOException, InterruptedException;
}
