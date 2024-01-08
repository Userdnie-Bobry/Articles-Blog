package org.articlesblog.jpa.repository;

import org.articlesblog.jpa.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
