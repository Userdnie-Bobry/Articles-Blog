package org.articlesblog.jpa.repository;

import org.articlesblog.jpa.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByTitleContainingIgnoreCase(String title);
    List<Article> findAllByDescriptionContainingIgnoreCase(String description);
    List<Article> findAllByAuthorContainingIgnoreCase(String author);
    List<Article> findAllByLabelContainingIgnoreCase(String label);
}
