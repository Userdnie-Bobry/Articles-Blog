package org.articlesblog;

import org.articlesblog.dto.articledto.CreateArticleDTO;
import org.articlesblog.dto.articledto.EditArticleDTO;
import org.articlesblog.dto.articledto.GetAllArticlesDTO;
import org.articlesblog.dto.articledto.GetArticleDTO;
import org.articlesblog.jpa.entity.Article;
import org.articlesblog.jpa.repository.ArticleRepository;
import org.articlesblog.services.article.ArticleService;
import org.articlesblog.services.article.ArticleServiceImpl;
import org.articlesblog.services.firebase.FirebaseStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApplicationTests {
	@Mock
	private ArticleRepository articleRepository;
	@Mock
	private FirebaseStorageService firebaseStorageService;
	private ArticleService articleService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		articleService = new ArticleServiceImpl(articleRepository, firebaseStorageService);
	}

	@Test
	void getArticle() {
		Integer id = 1;
		Article article = new Article();
		article.setId(id);
		article.setTitle("Title");
		article.setText("Text");
		article.setAuthor("Author");
		article.setLabel("Label");
		article.setDateCreate(LocalDateTime.now());
		article.setDateChange(LocalDateTime.now());
		article.setImage("Image");

		when(articleRepository.findById(id)).thenReturn(Optional.of(article));

		GetArticleDTO getArticleDTO = articleService.getArticle(id);
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
		String time = formatter.format(currentTime);

		assertEquals(id, getArticleDTO.getId());
		assertEquals("Title", getArticleDTO.getTitle());
		assertThat(getArticleDTO.getText().replaceAll("<[^>]*>", "")).isEqualToIgnoringWhitespace("Text");
		assertEquals("Author", getArticleDTO.getAuthor());
		assertEquals("Label", getArticleDTO.getLabel());
		assertEquals(time, getArticleDTO.getDateCreate());
		assertEquals(time, getArticleDTO.getDateChange());
		assertEquals("Image", getArticleDTO.getImage());
	}

	@Test
	void getArticle_whenArticleNotFound() {
		Integer id = 1;

		when(articleRepository.findById(id)).thenReturn(Optional.empty());

		GetArticleDTO getArticleDTO = articleService.getArticle(id);

		assertNull(getArticleDTO);
	}

	@Test
	void getToEditArticle() {
		Integer id = 1;
		Article article = new Article();
		article.setId(id);
		article.setTitle("Title");
		article.setDescription("Description");
		article.setText("Text");
		article.setAuthor("Author");
		article.setLabel("Label");
		article.setImage("Image");

		when(articleRepository.findById(id)).thenReturn(Optional.of(article));

		EditArticleDTO editArticleDTO = articleService.getToEditArticle(id);

		assertEquals(id, editArticleDTO.getId());
		assertEquals("Title", editArticleDTO.getTitle());
		assertEquals("Description", editArticleDTO.getDescription());
		assertEquals("Text", editArticleDTO.getText());
		assertEquals("Author", editArticleDTO.getAuthor());
		assertEquals("Label", editArticleDTO.getLabel());
		assertEquals("Image", editArticleDTO.getImage());
	}

	@Test
	void getToEditArticle_whenArticleNotFound() {
		Integer id = 1;

		when(articleRepository.findById(id)).thenReturn(Optional.empty());

		EditArticleDTO editArticleDTO = articleService.getToEditArticle(id);

		assertNull(editArticleDTO);
	}

	@Test
	void getAllArticles() {
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article();
		article1.setId(1);
		article1.setLabel("Label1");
		article1.setAuthor("Author1");
		article1.setTitle("Title1");
		article1.setDescription("Description1");
		article1.setDateCreate(LocalDateTime.now());
		article1.setImage("Image1");
		articles.add(article1);

		Article article2 = new Article();
		article2.setId(2);
		article2.setLabel("Label2");
		article2.setAuthor("Author2");
		article2.setTitle("Title2");
		article2.setDescription("Description2");
		article2.setDateCreate(LocalDateTime.now());
		article2.setImage("Image2");
		articles.add(article2);

		when(articleRepository.findAllSortedById()).thenReturn(articles);

		List<GetAllArticlesDTO> getAllArticlesDTOS = articleService.getAllArticles();
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
		String time = formatter.format(currentTime);

		assertEquals(2, getAllArticlesDTOS.size());
		assertEquals(2, getAllArticlesDTOS.get(0).getId());
		assertEquals("Label2", getAllArticlesDTOS.get(0).getLabel());
		assertEquals("Author2", getAllArticlesDTOS.get(0).getAuthor());
		assertEquals("Title2", getAllArticlesDTOS.get(0).getTitle());
		assertEquals("Description2", getAllArticlesDTOS.get(0).getDescription());
		assertEquals(time, getAllArticlesDTOS.get(0).getDateCreate());
		assertEquals("Image2", getAllArticlesDTOS.get(0).getImage());

		assertEquals(1, getAllArticlesDTOS.get(1).getId());
		assertEquals("Label1", getAllArticlesDTOS.get(1).getLabel());
		assertEquals("Author1", getAllArticlesDTOS.get(1).getAuthor());
		assertEquals("Title1", getAllArticlesDTOS.get(1).getTitle());
		assertEquals("Description1", getAllArticlesDTOS.get(1).getDescription());
		assertEquals(time, getAllArticlesDTOS.get(1).getDateCreate());
		assertEquals("Image1", getAllArticlesDTOS.get(1).getImage());
	}

	@Test
	void getAllArticles_whenArticlesNotFound() {
		when(articleRepository.findAllSortedById()).thenReturn(new ArrayList<>());

		List<GetAllArticlesDTO> getAllArticlesDTOS = articleService.getAllArticles();

		assertEquals(0, getAllArticlesDTOS.size());
	}

	@Test
	public void testCreateArticle() {
		CreateArticleDTO articleDTO = new CreateArticleDTO();
		articleDTO.setTitle("Test Title");
		articleDTO.setDescription("Test Description");
		articleDTO.setAuthor("Test Author");
		articleDTO.setLabel("Test Label");
		articleDTO.setText("Test Text");
		articleDTO.setMultipartFile(null);

		Article mockArticle = new Article();
		mockArticle.setId(1);
		mockArticle.setTitle(articleDTO.getTitle());
		mockArticle.setDescription(articleDTO.getDescription());
		mockArticle.setAuthor(articleDTO.getAuthor());
		mockArticle.setLabel(articleDTO.getLabel());
		mockArticle.setText(articleDTO.getText());
		mockArticle.setImage("Test Image URL");
		mockArticle.setDateCreate(LocalDateTime.now());
		mockArticle.setDateChange(LocalDateTime.now());

		when(firebaseStorageService.uploadImage(any())).thenReturn("Test Image URL");
		when(articleRepository.save(any(Article.class))).thenReturn(mockArticle);

		EditArticleDTO result = articleService.createArticle(articleDTO);

		assertEquals(mockArticle.getId(), result.getId());
		assertEquals(mockArticle.getTitle(), result.getTitle());
		assertEquals(mockArticle.getDescription(), result.getDescription());
		assertEquals(mockArticle.getText(), result.getText());
		assertEquals(mockArticle.getAuthor(), result.getAuthor());
		assertEquals(mockArticle.getLabel(), result.getLabel());
		assertEquals(mockArticle.getImage(), result.getImage());
	}

	@Test
	public void testEditArticle() {
		Integer id = 1;
		CreateArticleDTO articleDTO = new CreateArticleDTO();
		articleDTO.setTitle("Test Title");
		articleDTO.setDescription("Test Description");
		articleDTO.setAuthor("Test Author");
		articleDTO.setLabel("Test Label");
		articleDTO.setText("Test Text");
		articleDTO.setMultipartFile(mock(MultipartFile.class));

		Article existingArticle = new Article();
		existingArticle.setId(id);
		existingArticle.setTitle("Existing Title");
		existingArticle.setDescription("Existing Description");
		existingArticle.setAuthor("Existing Author");
		existingArticle.setLabel("Existing Label");
		existingArticle.setText("Existing Text");
		existingArticle.setImage("Existing Image URL");
		existingArticle.setDateCreate(LocalDateTime.now());
		existingArticle.setDateChange(LocalDateTime.now());

		when(articleRepository.findById(id)).thenReturn(Optional.of(existingArticle));
		when(firebaseStorageService.updateImage(any(), any())).thenReturn("Updated Image URL");
		when(articleRepository.save(any(Article.class))).thenReturn(existingArticle);

		EditArticleDTO result = articleService.editArticle(id, articleDTO);

		assertEquals(existingArticle.getId(), result.getId());
		assertEquals(articleDTO.getTitle(), result.getTitle());
		assertEquals(articleDTO.getDescription(), result.getDescription());
		assertEquals(articleDTO.getText(), result.getText());
		assertEquals(articleDTO.getAuthor(), result.getAuthor());
		assertEquals(articleDTO.getLabel(), result.getLabel());
		assertEquals("Updated Image URL", result.getImage());
	}

	@Test
	public void testEditArticle_NotFound() {
		Integer id = 1;
		CreateArticleDTO articleDTO = new CreateArticleDTO();

		when(articleRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> articleService.editArticle(id, articleDTO));
	}

	@Test
	void deleteArticle() {
		Integer id = 1;
		Article article = new Article();
		article.setId(id);
		article.setImage("image-url");

		when(articleRepository.findById(id)).thenReturn(Optional.of(article));

		String result = articleService.deleteArticle(id);

		assertEquals("Статья " + id + " удалена", result);
	}

	@Test
	void deleteArticle_ArticleNotFound() {
		Integer id = 1;

		when(articleRepository.findById(id)).thenReturn(Optional.empty());

		String result = articleService.deleteArticle(id);

		assertEquals("Статья не найдена", result);
	}

	@Test
	void markdownToHTML() {
		String markdown = "# This is some **markdown** text.";

		String html = articleService.markdownToHTML(markdown);
		System.out.println(html);

		assertEquals("<h1>This is some <strong>markdown</strong> text.</h1>", html.trim());
	}
}
