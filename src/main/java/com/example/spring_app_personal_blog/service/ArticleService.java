package com.example.spring_app_personal_blog.service;

import com.example.spring_app_personal_blog.dto.ArticleDTO;
import com.example.spring_app_personal_blog.entity.Article;
import com.example.spring_app_personal_blog.entity.User;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article createArticle(ArticleDTO articleDTO, User user);

    List<Article> getAllArticles();

    Optional<Article> getArticleById(Long id);

    Article updateArticle(Long id, ArticleDTO articleDTO);

    void deleteArticle(Long id);
}
