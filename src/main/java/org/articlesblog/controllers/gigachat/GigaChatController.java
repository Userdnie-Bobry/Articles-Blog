package org.articlesblog.controllers.gigachat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.articledto.GetArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.articlesblog.services.gigachat.GigaChatService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "GigaChat")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GigaChatController {
    @Value("${gigachat.promt.summary}")
    private String promtSummary;

    @Value("${gigachat.promt.article}")
    private String promtArticle;

    private final GigaChatService gigaChatService;
    private final ArticleService articleService;

    @GetMapping("/summary/{id}")
    @Operation(summary = "Получение пересказа статьи")
    public ResponseEntity<String> generateSummary(@PathVariable Integer id, Model model) {
        GetArticleDTO article = articleService.getArticle(id);
        String text = Jsoup.parse(article.getText()).text();
        log.info(text);
        String summary = gigaChatService.getAnswer(text, promtSummary);
        log.info(summary);

        return ResponseEntity.ok(summary);
    }

    @PostMapping("/generate")
    @Operation(summary = "Генерация статьи")
    public ResponseEntity<String> generateArticle(@RequestBody Map<String, String> articleColumns) {
        String articleText = articleColumns.get("articleColumns");
        log.info("Получены колонки articleColumns: " + articleText);

        String text = Jsoup.parse(articleText).text();

        String generatedArticleText = gigaChatService.getAnswer(text, promtArticle);
        log.info("Сгенерированный текст статьи: " + generatedArticleText);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(generatedArticleText);
    }
}