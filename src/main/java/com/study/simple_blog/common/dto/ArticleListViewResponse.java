package com.study.simple_blog.common.dto;

import com.study.simple_blog.common.entity.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {

    private final long id;  // 아티클 ID
    private final String title; // 제목
    private final String content; // 내용

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
