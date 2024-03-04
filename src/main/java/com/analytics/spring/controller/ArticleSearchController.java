package com.analytics.spring.controller;

import com.analytics.spring.dto.Company;
import com.analytics.spring.service.ICompanyArticleProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/articles")
public class ArticleSearchController {

    @Autowired
    private ICompanyArticleProcessorService companyArticleProcessorService;

    @GetMapping("/search-companies")
    public Flux<Company> searchArticles() throws IOException {
        return companyArticleProcessorService.findCompaniesMentionedInArticles();
    }
}
