package org.articlesblog.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GigaChatConfig {
    String rules = " Напиши всё одной строкой без переносов (/n).";
    String promtSummary = "Перескажи этот текст своими словами, сохраняя его основной смысл и структуру." + rules;
    String promtArticle = "На основе предоставленного текста напиши подробную и понятную статью в стиле профессионального статьевика.";
}
