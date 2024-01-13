package com.study.simple_blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.simple_blog.api.repoistory.BlogRepository;
import com.study.simple_blog.api.service.BlogService;
import com.study.simple_blog.common.dto.ArticleDto;
import com.study.simple_blog.common.entity.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class JpaTest {

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Mock
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer("postgres:14");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        postgresContainer.start(); // 매 테스트 시작 시 컨테이너 시작
    }

    @AfterEach
    void tearDown() {
        postgresContainer.stop(); // 매 테스트 끝난 뒤 컨테이너 종료
    }

    @DisplayName("테스트 컨테이너를 이용한 데이터 저장")
    @Test
    void containerSave() throws Exception {
        // given
        final String title = "title";
        final String content = "content";
        final ArticleDto articleDto = new ArticleDto(title, content);

        // 객체 Json 으로 직렬화
        Article article = blogRepository.save(articleDto.toEntity());

        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }
}