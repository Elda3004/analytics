package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Article;
import com.analytics.spring.dto.Company;
import com.analytics.spring.dto.CompanyArticleRecord;
import com.analytics.spring.service.IArticleReaderService;
import com.analytics.spring.service.ICompanyArticleProcessorService;
import com.analytics.spring.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Service
public class CompanyArticleProcessorServiceImpl implements ICompanyArticleProcessorService {
    @Autowired
    private IArticleReaderService articleReaderService;
    @Autowired
    private ICompanyService companyService;

    public Flux<CompanyArticleRecord> findCompaniesMentionedInArticles() throws IOException {
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

    private ParallelFlux<CompanyArticleRecord> findCompaniesInArticle(Flux<Company> companies, Article article) {
        return companies
                .parallel()
                .runOn(Schedulers.parallel())
                .filter(company -> !company.getName().isBlank() && !company.getName().isEmpty())
                .filter(company -> articleContainsCompany(article, company))
                .flatMap(company -> Mono.just(new CompanyArticleRecord(company.getName(), article.getTitle())));
    }

    private boolean articleContainsCompany(Article article, Company company) {
        return article.getContent().contains(company.getName());
        // match the whole word only in company name
//        String regex = "\\b"+Pattern.quote(company.getName())+"\\b";
//        Pattern pattern = Pattern.compile(regex);
//
//        Matcher matcher = pattern.matcher(article.getContent());
//
//        if (matcher.find()) {
//            int contentLength = matcher.end() - matcher.start();
//            return contentLength == company.getName().length();
//        }
//        return false;
    }
}
