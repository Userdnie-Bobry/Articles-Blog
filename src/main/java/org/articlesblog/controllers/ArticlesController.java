package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.ArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Article's blog")
@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticlesController {
    private final ArticleService articleService;

    @GetMapping("/get/article/{id}")
    @Operation(summary = "Получение данных статьи по id")
    public String getArticle(@PathVariable Integer id, Model model) {
        ArticleDTO articleDTO = articleService.getArticle(id);
        model.addAttribute("article", articleDTO);
        model.addAttribute("title", articleDTO.getTitle());
        return "get-article";
    }

    // появится после авторизации + настройки доступов
    @PutMapping("/edit/article/{id}")
    @Operation(summary = "Обновление данных статьи по id")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Integer id, @RequestBody ArticleDTO articleDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    // появится после авторизации + настройки доступов
    @PostMapping("/create/article")
    @Operation(summary = "Создание новой статьи")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.ok(createdArticle);
    }

    // появится после авторизации + настройки доступов. будет внутри просмотра статьи
    @DeleteMapping("/delete/article/{id}")
    @Operation(summary = "Удаление статьи по id")
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
