package org.articlesblog.services.article;

import org.articlesblog.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO getArticle(Integer id);
    List<ArticleDTO> getAllArticles();
    ArticleDTO createArticle(ArticleDTO articleDTO);
    ArticleDTO updateArticle(Integer id, ArticleDTO articleDTO);
    String deleteArticle(Integer id);
}
