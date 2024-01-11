package com.study.simple_blog.common.dto;

import com.study.simple_blog.common.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArticleDto {

    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

    public ArticleDto(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
