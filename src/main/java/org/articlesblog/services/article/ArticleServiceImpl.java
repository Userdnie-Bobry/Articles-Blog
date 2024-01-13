package org.articlesblog.services.article;

import com.google.firebase.database.annotations.NotNull;
import lombok.RequiredArgsConstructor;
import org.articlesblog.dto.ArticleDTOInput;
import org.articlesblog.dto.ArticleDTOOutput;
import org.articlesblog.jpa.entity.Article;
import org.articlesblog.jpa.repository.ArticleRepository;
import org.articlesblog.services.firebase.FirebaseStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    private final FirebaseStorageService firebaseStorageService;

    @Override
    public ArticleDTOOutput getArticle(Integer id) {
        return articleRepository.findById(id)
                .map(article -> {
                    String dateChange = article.getDateChange() != null ? article.getDateChange().toString() : "-";
                    return new ArticleDTOOutput(article.getId(), article.getTitle(), article.getDescription(),
                            article.getAuthor(),  article.getLabel(), article.getImage(), article.getDateCreate().toString(), dateChange);
                })
                .orElse(null);
    }

    @Override
    public List<ArticleDTOOutput> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return getArticleDTOS(articles);
    }

    @Transactional
    @Override
    public ArticleDTOOutput createArticle(ArticleDTOInput articleDTOInput) {
        String fileName = firebaseStorageService.uploadImage(articleDTOInput.getMultipartFile());

        Article article = new Article();
        article.setTitle(articleDTOInput.getTitle());
        article.setDescription(articleDTOInput.getDescription());
        article.setAuthor(articleDTOInput.getAuthor());
        article.setLabel(articleDTOInput.getLabel());
        article.setImage(fileName);
        article.setDateCreate(new Date());
        article.setDateChange(null);

        Article savedArticle = articleRepository.save(article);
        return new ArticleDTOOutput(savedArticle.getId(), savedArticle.getTitle(), savedArticle.getDescription(),
                savedArticle.getAuthor(), article.getLabel(), article.getImage(), savedArticle.getDateCreate().toString(), null);
    }

    @Transactional
    @Override
    public ArticleDTOOutput updateArticle(Integer id, ArticleDTOInput articleDTOInput) {
        Article article = articleRepository.findById(id)
                .map(existingArticle -> {

                    String fileName = firebaseStorageService.updateImage(existingArticle.getImage(), articleDTOInput.getMultipartFile());

                    existingArticle.setTitle(articleDTOInput.getTitle());
                    existingArticle.setDescription(articleDTOInput.getDescription());
                    existingArticle.setAuthor(articleDTOInput.getAuthor());
                    existingArticle.setLabel(articleDTOInput.getLabel());
                    existingArticle.setImage(fileName);
                    existingArticle.setDateChange(new Date());
                    return articleRepository.save(existingArticle);
                })
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));

        return new ArticleDTOOutput(article.getId(), article.getTitle(), article.getDescription(),
                article.getAuthor(), article.getLabel(), article.getImage(), article.getDateCreate().toString(), article.getDateChange().toString());
    }

    @Transactional
    @Override
    public String deleteArticle(Integer id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        return articleOptional.map(article -> {
            articleRepository.deleteById(id);
            firebaseStorageService.deleteImage(article.getImage());
            return "Статья " + id + " удалена";
        }).orElse("Неудачное удаление статьи");
    }

    @Override
    public List<ArticleDTOOutput> searchArticles(String searchText) {
        String searchTextIgnoreCase = searchText.toLowerCase();

        List<Article> articles = new ArrayList<>();
        articles.addAll(articleRepository.findAllByTitleContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByDescriptionContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByAuthorContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByLabelContainingIgnoreCase(searchTextIgnoreCase));

        articles = articles.stream().distinct().collect(Collectors.toList());

        return getArticleDTOS(articles);
    }

    @NotNull
    private List<ArticleDTOOutput> getArticleDTOS(List<Article> articles) {
        List<ArticleDTOOutput> articleDTOOutputs = new ArrayList<>();
        for (Article article : articles) {
            Optional<Date> dateChangeOptional = Optional.ofNullable(article.getDateChange());
            String dateChange = dateChangeOptional.map(Date::toString).orElse("-");
            ArticleDTOOutput articleDTOOutput = new ArticleDTOOutput(
                    article.getId(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getAuthor(),
                    article.getLabel(),
                    article.getImage(),
                    article.getDateCreate().toString(),
                    dateChange
            );
            articleDTOOutputs.add(0, articleDTOOutput);
        }
        return articleDTOOutputs;
    }

}