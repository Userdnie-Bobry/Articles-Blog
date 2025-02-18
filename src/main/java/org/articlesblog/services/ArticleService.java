package org.articlesblog.services;

import org.articlesblog.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO getArticle(Integer id);
    List<ArticleDTO> getAllArticles();
    ArticleDTO createArticle(ArticleDTO articleDTO);
    ArticleDTO updateArticle(Integer id, ArticleDTO articleDTO);
    void deleteArticle(Integer id);
}
