package org.articlesblog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.dto.articledto.GetAllArticlesDTO;
import org.articlesblog.services.article.ArticleService;
import org.articlesblog.services.hibernatesearch.SearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Tag(name = "Main page")
@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final ArticleService articleService;
    private final SearchService searchService;
    @GetMapping("/")
    @Operation(summary = "Переадресация на главную страницу")
    public String slashArticles(){
        return "redirect:/articles/page/1";
    }

    @GetMapping("/articles")
    @Operation(summary = "Переадресация на главную страницу")
    public String anotherSlashArticles(){
        return "redirect:/articles/page/1";
    }

    @GetMapping("/articles/page/{id}")
    @Operation(summary = "Получение статей по страницам")
    public String getArticlesByPage(@PathVariable Integer id, Model model) {
        int pageSize = 9;
        List<GetAllArticlesDTO> articles = articleService.getAllArticles();

        int totalPages = (int) Math.ceil((double) articles.size() / pageSize);
        int startIndex = (id - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, articles.size());

        if (id < 1 || id > totalPages) {
            return "articles";
        }

        List<GetAllArticlesDTO> articlesOnPage = new ArrayList<>();

        for (int i = endIndex - 1; i >= startIndex; i--) {
            articlesOnPage.add(articles.get(i));
        }

        Collections.reverse(articlesOnPage);

        model.addAttribute("articles", articlesOnPage);
        model.addAttribute("title", "Страница " + id + " из " + totalPages);
        model.addAttribute("currentPage", id);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searched", false);

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
    @Operation(summary = "Поиск")
    public String searchArticles(@RequestParam(value = "searchText") String searchText,
                                 @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDate,
                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endDate,
                                 Model model) {

        List<GetAllArticlesDTO> articles;

        if (searchText.isEmpty()) {
            if (startDate.isPresent() && endDate.isPresent()) {
                articles = searchService.searchByPeriod(startDate.get(), endDate.get());
                log.info("Поиск только по периоду");
            } else if (startDate.isPresent()) {
                articles = searchService.searchByStartDate(startDate.get());
                log.info("Поиск только c даты");
            } else if (endDate.isPresent()) {
                articles = searchService.searchByEndDate(endDate.get());
                log.info("Поиск только до даты");
            } else {
                log.info("всё пусто");
                return "redirect:/articles/page/1";
            }
        }
        else {
            if (startDate.isPresent() && endDate.isPresent()) {
                articles = searchService.searchByQueryAndPeriod(searchText, startDate.get(), endDate.get());
                log.info("Поиск по периоду и строке");
            } else if (startDate.isPresent()) {
                articles = searchService.searchByQueryAndStartDate(searchText, startDate.get());
                log.info("Поиск c даты и строке");
            } else if (endDate.isPresent()) {
                articles = searchService.searchByQueryAndEndDate(searchText, endDate.get());
                log.info("Поиск до даты и строке");
            } else {
                articles = searchService.searchByQuery(searchText);
                log.info("Поиск только по строке");
            }
        }

        model.addAttribute("articles", articles);
        model.addAttribute("searched", true);
        return "articles";
    }
}
