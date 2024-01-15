package org.articlesblog.services.article;

import org.articlesblog.dto.*;

import java.util.List;

public interface ArticleService {
    GetArticleDTO getArticle(Integer id);
    EditArticleDTO getToEditArticle(Integer id);
    List<MainPageArticleDTO> getAllArticles();
    void createArticle(EditArticleDTO articleDTO);
    void updateArticle(Integer id, EditArticleDTO articleDTO);
    void deleteArticle(Integer id);
    List<SearchArticleDTO> searchArticles(String searchText);
}
