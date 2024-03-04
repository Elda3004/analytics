package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Article;
import com.analytics.spring.dto.Company;
import com.analytics.spring.dto.CompanyArticleRecord;
import com.analytics.spring.service.IArticleReaderService;
import com.analytics.spring.service.ICompanyArticleProcessorService;
import com.analytics.spring.service.ICompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CompanyArticleProcessorServiceImplTest {
    @MockBean
    private IArticleReaderService articleReaderService;

    @MockBean
    private ICompanyService companyService;

    @MockBean
    private ICompanyArticleProcessorService service;

    @Test
    public void testFindCompaniesMentionedInArticles() throws IOException {
        // Mock articles and companies
        Article article1 = new Article("Article 1", "This is a test article mentioning European Bank");
        Article article2 = new Article("Article 2", "This is another test article mentioning York");
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("European Bank"));
        companies.add(new Company("York"));

        // Stub methods in mock services
        when(articleReaderService.processArticles()).thenReturn(Flux.just(article1, article2));
        when(companyService.processCompanies()).thenReturn(Flux.fromIterable(companies));

        // Call the method to test
        Flux<CompanyArticleRecord> resultFlux = service.findCompaniesMentionedInArticles();

        // Verify the results using StepVerifier
        StepVerifier.create(resultFlux)
                .expectNextMatches(record -> record.companyName().equals("European Bank") && record.articleTitle().equals("Article 1"))
                .expectNextMatches(record -> record.companyName().equals("York") && record.articleTitle().equals("Article 2"))
                .expectComplete()
                .verify();

        verify(articleReaderService, times(1)).processArticles();
        verify(companyService, times(1)).processCompanies();
    }
}

