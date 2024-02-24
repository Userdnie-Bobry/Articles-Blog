package org.articlesblog.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GigaChatConfig {
    final String promtSummary = "Перескажи этот текст своими словами, сохраняя его основной смысл и структуру.";
    final String promtArticle = "На основе предоставленного текста напиши подробную и понятную статью в стиле профессионального статьевика.";
}
