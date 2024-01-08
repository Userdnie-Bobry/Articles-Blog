package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.ArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Article's blog")
@Slf4j
@RequiredArgsConstructor
@Controller
public class GetArticlesController {
    private final ArticleService articleService;

    @GetMapping("/articles/{id}")
    @Operation(summary = "Получение данных статьи по id")
    public String getArticle(@PathVariable Integer id, Model model) {
        ArticleDTO article = articleService.getArticle(id);
        model.addAttribute("article", article);
        model.addAttribute("title", article.getTitle());
        return "article/get-article";
    }

    @GetMapping("/articles/new")
    @Operation(summary = "Создание новой статьи")
    public String createArticleGet() {
        return "article/create-article";
    }

    @GetMapping("/articles/edit/{id}")
    @Operation(summary = "Обновление данных статьи по id")
    public String updateArticleGet(@PathVariable Integer id, Model model) {
        ArticleDTO article = articleService.getArticle(id);
        model.addAttribute("article", article);
        model.addAttribute("title", article.getTitle());
        return "article/edit-article";
    }
}
