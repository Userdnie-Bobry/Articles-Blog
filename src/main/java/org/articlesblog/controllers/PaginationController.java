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

@Tag(name = "Pagination")
@Slf4j
@RequiredArgsConstructor
@Controller
public class PaginationController {
    private final ArticleService articleService;
    private final SearchService searchService;
    private boolean searched;

    @GetMapping("/articles/page/{id}")
    @Operation(summary = "Получение статей по страницам")
    public String getArticlesByPage(@PathVariable Integer id, Model model) {
        List<GetAllArticlesDTO> articles = articleService.getAllArticles();

        model.addAttribute("searched", false);
        searched = false;
        return getPages(id, model, articles, false);
    }

    @GetMapping("/articles/search/page/{id}")
    @Operation(summary = "Поиск")
    public String searchArticles(@PathVariable Integer id,
                                 @RequestParam(value = "searchText") String searchText,
                                 @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDate,
                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endDate,
                                 Model model) {

        List<GetAllArticlesDTO> articles;

        if (searchText.isEmpty()) {
            if (startDate.isPresent() && endDate.isPresent()) {
                articles = searchService.searchByPeriod(startDate.get(), endDate.get());
                model.addAttribute("startDate", startDate.orElse(null));
                model.addAttribute("endDate", endDate.orElse(null));
                log.info("Поиск только по периоду");
            } else if (startDate.isPresent()) {
                articles = searchService.searchByStartDate(startDate.get());
                model.addAttribute("startDate", startDate.orElse(null));
                log.info("Поиск только c даты");
            } else if (endDate.isPresent()) {
                articles = searchService.searchByEndDate(endDate.get());
                model.addAttribute("endDate", endDate.orElse(null));
                log.info("Поиск только до даты");
            } else {
                log.info("всё пусто");
                return "redirect:/articles/page/1";
            }
        } else {
            model.addAttribute("searchText", searchText);
            if (startDate.isPresent() && endDate.isPresent()) {
                articles = searchService.searchByQueryAndPeriod(searchText, startDate.get(), endDate.get());
                model.addAttribute("startDate", startDate.orElse(null));
                model.addAttribute("endDate", endDate.orElse(null));
                log.info("Поиск по периоду и строке");
            } else if (startDate.isPresent()) {
                articles = searchService.searchByQueryAndStartDate(searchText, startDate.get());
                model.addAttribute("startDate", startDate.orElse(null));
                log.info("Поиск c даты и строке");
            } else if (endDate.isPresent()) {
                articles = searchService.searchByQueryAndEndDate(searchText, endDate.get());
                model.addAttribute("endDate", endDate.orElse(null));
                log.info("Поиск до даты и строке");
            } else {
                articles = searchService.searchByQuery(searchText);
                log.info("Поиск только по строке");
            }
        }

        model.addAttribute("searched", true);
        searched = true;

        if (articles.isEmpty()){
            return "articles";
        }
        else {
            model.addAttribute("articles", articles);
            return getPages(id, model, articles, searched);
        }
    }

    private String getPages(Integer id, Model model, List<GetAllArticlesDTO> articles, boolean searched) {
        int pageSize = 9;
        int totalPages = (int) Math.ceil((double) articles.size() / pageSize);
        int startIndex = (id - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, articles.size());

        if(articles.isEmpty()){
            return "articles";
        }
        else if (id > totalPages) {
            model.addAttribute("errorMes", "-Максимальная страница: " + totalPages);
            return "error";
        }
        else if (id < 1){
            model.addAttribute("errorMes", "-Минимальная страница: 1");
            return "error";
        }

        List<GetAllArticlesDTO> articlesOnPage = new ArrayList<>();

        for (int i = endIndex - 1; i >= startIndex; i--) {
            articlesOnPage.add(articles.get(i));
        }

        if (!searched){
            Collections.reverse(articlesOnPage);
        }

        model.addAttribute("articles", articlesOnPage);
        model.addAttribute("title", "Страница " + id + " из " + totalPages);
        model.addAttribute("currentPage", id);
        model.addAttribute("totalPages", totalPages);
        return "articles";
    }
}
