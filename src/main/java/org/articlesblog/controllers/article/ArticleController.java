package org.articlesblog.controllers.article;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.articledto.CreateArticleDTO;
import org.articlesblog.dto.articledto.EditArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Article's blog")
@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping("/articles/new")
    @Operation(summary = "Создание новой статьи")
    public ResponseEntity<EditArticleDTO> createArticle(@ModelAttribute CreateArticleDTO articleDTO) {
        EditArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.ok(createdArticle);
    }

    @PostMapping("/articles/edit/{id}")
    @Operation(summary = "Обновление данных статьи по id")
    public ResponseEntity<EditArticleDTO> updateArticle(@PathVariable Integer id, @ModelAttribute CreateArticleDTO articleDTO) {
        EditArticleDTO updatedArticle = articleService.editArticle(id, articleDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    @PostMapping("/articles/delete/{id}")
    @Operation(summary = "Удаление статьи по id")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer id) {
        String deletedArticle = articleService.deleteArticle(id);
        return ResponseEntity.ok(deletedArticle);
    }
}
