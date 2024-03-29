package com.analytics.spring.commands;

import com.analytics.spring.dto.CompanyArticleRecord;
import com.analytics.spring.service.ICompanyArticleProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.io.IOException;

@ShellComponent
public class SearchCompaniesInArticlesCommand {
    @Autowired
    private ICompanyArticleProcessorService companyArticleProcessorService;

    @ShellMethod(value = "Search for list of companies in the given articles", key = "search-articles")
    public Disposable searchCompaniesMentionedInArticles() throws IOException {
        try {
            Flux<CompanyArticleRecord> companiesMentionedInArticles = companyArticleProcessorService
                    .findCompaniesMentionedInArticles();
            return  companiesMentionedInArticles.subscribe(
                    System.out::println,
                    System.err::println,
                    () -> System.out.println("Flux completed")
            );
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }
}
