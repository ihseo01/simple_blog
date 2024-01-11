package com.study.simple_blog.api.service;

import com.study.simple_blog.api.repoistory.BlogRepository;
import com.study.simple_blog.common.dto.ArticleDto;
import com.study.simple_blog.common.entity.Article;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BlogService {

    private final BlogRepository blogRepository;


    // 블로그 글 추가
    public Article save(ArticleDto articleDto) {
        return blogRepository.save(articleDto.toEntity());
    }

    // 블로그 글 전체 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 1건 조회
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 블로그 글 삭제
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정
    @Transactional
    public Article update(long id, ArticleDto articleDto) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(articleDto.getTitle(), articleDto.getContent());
        return article;
    }
}
