package com.study.simple_blog.api.controller;

import com.study.simple_blog.api.service.BlogService;
import com.study.simple_blog.common.dto.ArticleListViewResponse;
import com.study.simple_blog.common.dto.ArticleViewResponse;
import com.study.simple_blog.common.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if(id == null) {
            // id 가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            // id 가 있으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", article);
        }

        return "newArticle";
    }
}
