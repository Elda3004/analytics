package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Article;
import com.analytics.spring.dto.Company;
import com.analytics.spring.service.IArticleReaderService;
import com.analytics.spring.service.ICompanyArticleProcessorService;
import com.analytics.spring.service.ICompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Service
public class CompanyArticleProcessorServiceImpl implements ICompanyArticleProcessorService {
    @Autowired
    private IArticleReaderService articleReaderService;

    @Autowired
    private ICompanyService companyService;

    public Flux<Company> findCompaniesMentionedInArticles() throws IOException {
        return articleReaderService.processArticles()
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(article -> {
                    try {
                        return findCompaniesInArticle(companyService.processCompanies(), article);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sequential()
                .distinct();
    }

    private Flux<Company> findCompaniesInArticle(Flux<Company> companies, Article article) {
        return companies
                .filter(company -> articleContainsCompany(article, company))
                .flatMap(Mono::just)
                .subscribeOn(Schedulers.parallel());
    }

    private boolean articleContainsCompany(Article article, Company company) {
        return StringUtils.containsIgnoreCase(article.getContent(), company.getName());
    }
}
