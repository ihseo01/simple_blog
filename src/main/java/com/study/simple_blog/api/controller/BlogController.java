package com.study.simple_blog.api.controller;

import com.study.simple_blog.api.service.BlogService;
import com.study.simple_blog.common.dto.ArticleDto;
import com.study.simple_blog.common.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogController {

    private final BlogService blogService;

    // 블로그 글 추가
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleDto articleDto) {
        Article article = blogService.save(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }

    // 블로그 글 전체 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleDto>> findAllArticles() {
        List<ArticleDto> articles = blogService.findAll()
                .stream()
                .map(ArticleDto::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    // 블로그 글 1건 조회
    @GetMapping("/api/article/{id}")
    public ResponseEntity<ArticleDto> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleDto(article));
    }

    // 블로그 글 삭제
    @DeleteMapping("/api/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    // 블로그 글 수정
    @PutMapping("/api/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody ArticleDto articleDto) {
        Article updateArticle = blogService.update(id, articleDto);

        return ResponseEntity.ok()
                .body(updateArticle);
    }
}
