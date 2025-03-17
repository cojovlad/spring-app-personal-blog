package com.example.spring_app_personal_blog.service.impl;

import com.example.spring_app_personal_blog.dto.ArticleDTO;
import com.example.spring_app_personal_blog.entity.Article;
import com.example.spring_app_personal_blog.entity.User;
import com.example.spring_app_personal_blog.repository.ArticleRepository;
import com.example.spring_app_personal_blog.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepositoryInjection) {
        articleRepository = articleRepositoryInjection;
    }

    @Override
    public Article createArticle(ArticleDTO articleDTO, User user) {
        Article article = Article.builder()
                .title(articleDTO.getTitle())
                .content(articleDTO.getContent())
                .publishedAt(LocalDateTime.now())
                .user(user)
                .build();

        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article updateArticle(Long id, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());

        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found");
        }
        articleRepository.deleteById(id);
    }
}
