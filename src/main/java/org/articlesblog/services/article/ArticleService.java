package org.articlesblog.services.article;

import org.articlesblog.dto.articledto.*;

import java.util.List;

public interface ArticleService {
    GetArticleDTO getArticle(Integer id);
    EditArticleDTO getToEditArticle(Integer id);
    List<MainPageArticleDTO> getAllArticles();
    EditArticleDTO createArticle(CreateArticleDTO articleDTO);
    EditArticleDTO editArticle(Integer id, CreateArticleDTO articleDTO);
    String deleteArticle(Integer id);
    List<SearchArticleDTO> searchArticles(String searchText);
}
