package com.study.simple_blog.api.repoistory;

import com.study.simple_blog.common.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
