package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.ArticleDTO;
import org.articlesblog.services.ArticleService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //FIXME: в мейне этого метода не должно быть, либо в UserController, либо в AuthController
    @GetMapping("/auth")
    @Operation(summary = "Страница входа")
    public String auth(Model model){
        model.addAttribute("title", "Авторизация");
        return "login";
    }

    @GetMapping("/about")
    @RolesAllowed("admin")
    @Operation(summary = "О нас")
    public String about(Model model){
        model.addAttribute("title", "О нас");
        return "about";
    }
}
