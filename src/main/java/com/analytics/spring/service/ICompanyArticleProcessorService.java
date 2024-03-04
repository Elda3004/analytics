package com.analytics.spring.service;

import com.analytics.spring.dto.CompanyArticleRecord;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface ICompanyArticleProcessorService {

    Flux<CompanyArticleRecord> findCompaniesMentionedInArticles() throws IOException;
}
