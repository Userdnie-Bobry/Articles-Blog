package org.articlesblog.services.article;

import org.articlesblog.dto.ArticleDTOInput;
import org.articlesblog.dto.ArticleDTOOutput;

import java.util.List;

public interface ArticleService {
    ArticleDTOOutput getArticle(Integer id);
    List<ArticleDTOOutput> getAllArticles();
    ArticleDTOOutput createArticle(ArticleDTOInput articleDTOInput);
    ArticleDTOOutput updateArticle(Integer id, ArticleDTOInput articleDTOInput);
    String deleteArticle(Integer id);
    List<ArticleDTOOutput> searchArticles(String searchText);
}
