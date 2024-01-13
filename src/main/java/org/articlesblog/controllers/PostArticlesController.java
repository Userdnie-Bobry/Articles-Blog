package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.ArticleDTOInput;
import org.articlesblog.dto.ArticleDTOOutput;
import org.articlesblog.services.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Article's blog")
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostArticlesController {
    private final ArticleService articleService;

    @PostMapping("/articles/new")
    @Operation(summary = "Создание новой статьи")
    public String createArticle(@ModelAttribute ArticleDTOInput articleDTOInput) {
        articleService.createArticle(articleDTOInput);
        return "redirect:/articles/page/1";
    }

    @PostMapping("/articles/edit/{id}")
    @Operation(summary = "Обновление данных статьи по id")
    public String updateArticle(@PathVariable Integer id, @ModelAttribute ArticleDTOInput articleDTOInput) {
        articleService.updateArticle(id, articleDTOInput);
        return "redirect:/articles/page/1";
    }

    @PostMapping("/articles/delete/{id}")
    @Operation(summary = "Удаление статьи по id")
    public String deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return "redirect:/articles/page/1";
    }
}
