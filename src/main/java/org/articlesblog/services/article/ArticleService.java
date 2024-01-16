package org.articlesblog.services.article;

import org.articlesblog.dto.*;

import java.util.List;

public interface ArticleService {
    GetArticleDTO getArticle(Integer id);
    EditArticleDTO getToEditArticle(Integer id);
    List<MainPageArticleDTO> getAllArticles();
    EditArticleDTO createArticle(EditArticleDTO articleDTO);
    EditArticleDTO updateArticle(Integer id, EditArticleDTO articleDTO);
    String deleteArticle(Integer id);
    List<SearchArticleDTO> searchArticles(String searchText);
}
