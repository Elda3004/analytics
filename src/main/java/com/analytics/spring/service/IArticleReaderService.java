package com.analytics.spring.service;

import com.analytics.spring.dto.Article;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface IArticleReaderService {
    Flux<Article> processArticles() throws IOException;
}
