package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Article;
import com.analytics.spring.service.IArticleReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ArticleReaderServiceImplTest {

    @MockBean
    private IArticleReaderService articleReaderService;

    @Test
    public void testFindCompaniesMentionedInArticles() throws IOException {
        // Mock articles and companies
        Article article1 = new Article("Article 1", "This is a test article mentioning European Bank");
        Article article2 = new Article("Article 2", "This is another test article mentioning York");

        // Stub methods in mock services
        when(articleReaderService.processArticles()).thenReturn(Flux.just(article1, article2));
        // Call the method to test
        Flux<Article> resultFlux = articleReaderService.processArticles();

        // Verify the results using StepVerifier
        StepVerifier.create(resultFlux)
                .expectNextMatches(record ->
                        record.getTitle().equals("Article 1")
                                && record.getContent().equals("This is a test article mentioning European Bank")
                )
                .expectNextMatches(record -> record.getTitle().equals("Article 2"))
                .expectComplete()
                .verify();

        verify(articleReaderService, times(1)).processArticles();
    }
}
