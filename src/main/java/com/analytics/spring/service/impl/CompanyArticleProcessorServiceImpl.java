package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Article;
import com.analytics.spring.dto.Company;
import com.analytics.spring.service.IArticleReaderService;
import com.analytics.spring.service.ICompanyArticleProcessorService;
import com.analytics.spring.service.ICompanyService;
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
        return companyService.processCompanies().flatMap(company ->
                {
                    try {
                        return articleReaderService.processArticles()
                                .parallel()
                                .runOn(Schedulers.parallel())
                                .filter(article -> articleContainsCompany(article, company))
                                .flatMap(article -> Mono.just(company));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).distinct();
    }

    private boolean articleContainsCompany(Article article, Company company) {
        return article.getContent().contains(company.getName());
    }
}
