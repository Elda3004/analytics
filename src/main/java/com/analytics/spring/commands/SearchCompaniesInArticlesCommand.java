package com.analytics.spring.commands;

import com.analytics.spring.dto.Company;
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

    @ShellMethod(value = "Search for list of companies in the given articles", key = "search-companies")
    public Disposable searchCompaniesMentionedInArticles() throws IOException {
        try {
            Flux<Company> companyFlux = companyArticleProcessorService.findCompaniesMentionedInArticles();
            return  companyFlux.subscribe(
                    System.out::println,  // Print each emitted element to the console
                    System.err::println,  // Handle errors (if any)
                    () -> System.out.println("Flux completed")  // Handle completion of the Flux
            );
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }
}
