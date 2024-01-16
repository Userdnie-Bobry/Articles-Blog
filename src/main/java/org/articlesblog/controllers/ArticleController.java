package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.EditArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Article's blog")
@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping("/articles/new")
    //User
    @Operation(summary = "Создание новой статьи")
    public ResponseEntity<EditArticleDTO> createArticle(@RequestBody EditArticleDTO articleDTO) {
        EditArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.ok(createdArticle);
    }

    @PostMapping("/articles/edit/{id}")
    //User
    @Operation(summary = "Обновление данных статьи по id")
    public ResponseEntity<EditArticleDTO> updateArticle(@PathVariable Integer id, @RequestBody EditArticleDTO articleDTO) {
        EditArticleDTO updatedArticle = articleService.updateArticle(id, articleDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    @PostMapping("/articles/delete/{id}")
    //User
    @Operation(summary = "Удаление статьи по id")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer id) {
        String deletedArticle = articleService.deleteArticle(id);
        return ResponseEntity.ok(deletedArticle);
    }
}
