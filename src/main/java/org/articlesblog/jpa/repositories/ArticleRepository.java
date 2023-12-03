package org.articlesblog.jpa.repositories;

import org.articlesblog.jpa.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
