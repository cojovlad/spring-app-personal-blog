package com.example.spring_app_personal_blog.controller;

import com.example.spring_app_personal_blog.entity.Article;
import com.example.spring_app_personal_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    private final ArticleService articleService;

    @Autowired
    public HomeController(ArticleService articleServiceInjection) {
        articleService = articleServiceInjection;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("articles",  articleService.getAllArticles());
        return "index";
    }


    @GetMapping("/view/articles/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        model.addAttribute("article", article);
        return "article";
    }
}