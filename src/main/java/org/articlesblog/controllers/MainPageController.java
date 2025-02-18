package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.ArticleDTO;
import org.articlesblog.services.ArticleService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Main page")
@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class MainPageController {
    private final ArticleService articleService;

    @GetMapping("/")
    @Operation(summary = "Получение всех статей")
    public String getAllArticles(Model model) {
        List<ArticleDTO> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("title", "Все статьи");
        return "articles";
    }

    @GetMapping("/auth")
    @Operation(summary = "Страница входа")
    public String auth(Model model){
        model.addAttribute("title", "Авторизация");
        return "auth";
    }

    @GetMapping("/about")
    @Operation(summary = "О нас")
    public String about(Model model){
        model.addAttribute("title", "О нас");
        return "about";
    }
}
