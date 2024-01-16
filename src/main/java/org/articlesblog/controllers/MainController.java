package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.MainPageArticleDTO;
import org.articlesblog.dto.SearchArticleDTO;
import org.articlesblog.services.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Main page")
@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final ArticleService articleService;

    @GetMapping("/")
    @Operation(summary = "Стартовая страница")
    public String infiniteArticles(){
        return "redirect:/articles/page/1";
    }

    @GetMapping("/articles/page/{id}")
    @Operation(summary = "Получение статей по страницам")
    public String getArticlesByPage(@PathVariable Integer id, Model model) {
        int pageSize = 9;
        List<MainPageArticleDTO> articles = articleService.getAllArticles();

        int totalPages = (int) Math.ceil((double) articles.size() / pageSize);
        int startIndex = (id - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, articles.size());

        if (id < 1 || id > totalPages) {
            return "articles";
        }

        List<MainPageArticleDTO> articlesOnPage = articles.subList(startIndex, endIndex);
        model.addAttribute("articles", articlesOnPage);
        model.addAttribute("title", "Страница " + id + " из " + totalPages);
        model.addAttribute("currentPage", id);
        model.addAttribute("totalPages", totalPages);

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

    @GetMapping("/articles/search")
    public String searchArticles(@RequestParam("searchText") String searchText, Model model) {
        if (searchText.isEmpty()) {
            return "redirect:/articles/page/1";
        }

        List<SearchArticleDTO> articles = articleService.searchArticles(searchText);
        model.addAttribute("articles", articles);
        return "articles";
    }
}
