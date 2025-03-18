package com.example.spring_app_personal_blog.controller;

import com.example.spring_app_personal_blog.dto.ArticleDTO;
import com.example.spring_app_personal_blog.entity.Article;
import com.example.spring_app_personal_blog.entity.User;
import com.example.spring_app_personal_blog.service.ArticleService;
import com.example.spring_app_personal_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "dashboard";
    }

    @GetMapping("/add")
    public String addArticleForm(Model model) {
        model.addAttribute("articleDTO", new ArticleDTO());
        return "add_article";
    }

    @PostMapping("/add")
    public String addArticle(@ModelAttribute ArticleDTO articleDTO, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        articleService.createArticle(articleDTO, user);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id).orElseThrow();
        model.addAttribute("articleDTO", new ArticleDTO(article.getTitle(), article.getContent()));
        return "edit_article";
    }

    @PostMapping("/edit/{id}")
    public String editArticle(@PathVariable Long id, @ModelAttribute ArticleDTO articleDTO) {
        articleService.updateArticle(id, articleDTO);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "redirect:/admin/dashboard";
    }

}

