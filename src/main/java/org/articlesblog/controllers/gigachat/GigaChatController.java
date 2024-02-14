package org.articlesblog.controllers.gigachat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.articledto.GetArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.articlesblog.services.gigachat.GigaChatService;
import org.jsoup.Jsoup;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "GigaChat")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GigaChatController {
    private final GigaChatService gigaChatService;
    private final ArticleService articleService;

    @GetMapping("/summary/{id}")
    @Operation(summary = "Получение пересказа статьи")
    public ResponseEntity<String> generate(@PathVariable Integer id, Model model) throws IOException, InterruptedException {
        GetArticleDTO article = articleService.getArticle(id);
        String text = Jsoup.parse(article.getText()).text();
        log.info(text);
        String summary = gigaChatService.getAnswer(text);
        log.info(summary);
        model.addAttribute("id", id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(summary);
    }
}