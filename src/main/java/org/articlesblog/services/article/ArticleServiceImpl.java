package org.articlesblog.services.article;

import lombok.RequiredArgsConstructor;
import org.articlesblog.dto.articledto.*;
import org.articlesblog.jpa.entity.Article;
import org.articlesblog.jpa.repository.ArticleRepository;
import org.articlesblog.services.firebase.FirebaseStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;
    private final FirebaseStorageService firebaseStorageService;

    @Override
    public GetArticleDTO getArticle(Integer id) {
        return articleRepository.findById(id)
                .map(article -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
                    String createDate = article.getDateCreate().format(formatter);
                    String changeDate = article.getDateChange() != null ? article.getDateChange().format(formatter) : "-";


                    return new GetArticleDTO(article.getId(), article.getTitle(), article.getText(),
                            article.getAuthor(),  article.getLabel(), createDate, changeDate, article.getImage());
                })
                .orElse(null);
    }

    @Override
    public EditArticleDTO getToEditArticle(Integer id) {
        return articleRepository.findById(id)
                .map(article -> new EditArticleDTO(article.getId(), article.getTitle(), article.getDescription(), article.getText(),
                        article.getAuthor(),  article.getLabel(), article.getImage()))
                .orElse(null);
    }

    @Override
    public List<MainPageArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<MainPageArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
            String createDate = article.getDateCreate().format(formatter);
            MainPageArticleDTO articleDTO = new MainPageArticleDTO(
                    article.getId(),
                    article.getLabel(),
                    article.getAuthor(),
                    article.getTitle(),
                    article.getDescription(),
                    createDate,
                    article.getImage()
            );
            articleDTOs.add(0, articleDTO);
        }
        return articleDTOs;
    }

    @Override
    @Transactional
    public EditArticleDTO createArticle(CreateArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setDescription(articleDTO.getDescription());
        article.setAuthor(articleDTO.getAuthor());
        article.setLabel(articleDTO.getLabel());
        article.setText(articleDTO.getText());
        article.setImage(firebaseStorageService.uploadImage(articleDTO.getMultipartFile()));
        article.setDateCreate(LocalDateTime.now());

        Article savedArticle = articleRepository.save(article);
        return new EditArticleDTO(savedArticle.getId(), savedArticle.getTitle(), savedArticle.getDescription(), savedArticle.getText(),
                savedArticle.getAuthor(), article.getLabel(), article.getImage());
    }

    @Override
    @Transactional
    public EditArticleDTO editArticle(Integer id, CreateArticleDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .map(existingArticle -> {
                    existingArticle.setTitle(articleDTO.getTitle());
                    existingArticle.setDescription(articleDTO.getDescription());
                    existingArticle.setAuthor(articleDTO.getAuthor());
                    existingArticle.setLabel(articleDTO.getLabel());
                    existingArticle.setText(articleDTO.getText());
                    existingArticle.setImage(firebaseStorageService.updateImage(existingArticle.getImage(), articleDTO.getMultipartFile()));
                    existingArticle.setDateChange(LocalDateTime.now());

                    return articleRepository.save(existingArticle);
                })
                .orElseThrow(() -> new RuntimeException("Статья с id " + id + " не найдена."));

        return new EditArticleDTO(article.getId(), article.getTitle(), article.getDescription(), article.getText(),
                article.getAuthor(), article.getLabel(), article.getImage());
    }

    @Override
    @Transactional
    public String deleteArticle(Integer id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        return articleOptional.map(article -> {
            articleRepository.deleteById(id);
            try {
                firebaseStorageService.deleteImage(article.getImage());
            }
            catch (NullPointerException e){
                return "Картинка удалена";
            }
            return "Статья " + id + " удалена";
        }).orElse("Статья не найдена");
    }

    @Override
    public List<SearchArticleDTO> searchArticles(String searchText) {
        String searchTextIgnoreCase = searchText.toLowerCase();

        List<Article> articles = new ArrayList<>();
        articles.addAll(articleRepository.findAllByTitleContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByDescriptionContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByTextContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByAuthorContainingIgnoreCase(searchTextIgnoreCase));
        articles.addAll(articleRepository.findAllByLabelContainingIgnoreCase(searchTextIgnoreCase));

        articles = articles.stream().distinct().collect(Collectors.toList());

        return getSearchArticleDTOS(articles);
    }

    private static List<SearchArticleDTO> getSearchArticleDTOS(List<Article> articles) {
        List<SearchArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
            String createDate = article.getDateCreate().format(formatter);
            SearchArticleDTO articleDTO = new SearchArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getText(),
                    article.getAuthor(),
                    article.getLabel(),
                    createDate
            );
            articleDTOs.add(0, articleDTO);
        }
        return articleDTOs;
    }
}