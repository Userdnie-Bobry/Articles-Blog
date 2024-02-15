package org.articlesblog.services.gigachat;

import java.io.IOException;

public interface GigaChatService {
    String getAccessToken();
    String getModel();
    String getAnswer(String content, String promt);
}
