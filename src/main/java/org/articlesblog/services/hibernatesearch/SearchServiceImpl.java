package org.articlesblog.services.hibernatesearch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.articlesblog.dto.articledto.GetAllArticlesDTO;
import org.articlesblog.jpa.entity.Article;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<GetAllArticlesDTO> searchBy(String searchQuery) {
        SearchSession searchSession = Search.session(entityManager);

        List<Article> articles = searchSession.search(Article.class)
                .where(f -> f.bool()
                        .must(f.match().fields("title", "description", "text", "author", "label").matching(searchQuery))
//                        .should(f.match().field("dateCreate").matching(searchQuery))
//                        .should(f.match().field("dateChange").matching(searchQuery))
                )
                .fetchHits(20);

        return convertToSearchArticleDTO(articles);
    }

    private List<GetAllArticlesDTO> convertToSearchArticleDTO(List<Article> articles) {
        List<GetAllArticlesDTO> searchResults = new ArrayList<>();
        for (Article article : articles) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
            String createDate = article.getDateCreate().format(formatter);
            GetAllArticlesDTO articleDTO = new GetAllArticlesDTO(
                    article.getId(),
                    article.getLabel(),
                    article.getAuthor(),
                    article.getTitle(),
                    article.getDescription(),
                    createDate,
                    article.getImage()
            );
            searchResults.add(articleDTO);
        }
        return searchResults;
    }
}
