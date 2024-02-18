package org.articlesblog.services.hibernatesearch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.jpa.entity.Article;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class IndexServiceImpl implements IndexService{
    private final EntityManagerFactory entityManagerFactory;

    public void initiateIndexing() {
        log.info("Инициируется индексация...");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            SearchSession searchSession = Search.session(entityManager);
            MassIndexer indexer = searchSession.massIndexer(Article.class);
            indexer.start();
            log.info("Сущности проиндексированы");
        }
    }
}

