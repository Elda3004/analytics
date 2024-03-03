package com.analytics.spring.controller;

import com.analytics.spring.dto.ArticleDto;
import com.analytics.spring.dto.CompanyDto;
import com.analytics.spring.service.IArticleService;
import com.analytics.spring.service.ICompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import reactor.core.publisher.Flux;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICompanyService companyService;

    @GetMapping
    public List<ArticleDto> processArticlesContent() throws IOException, ParserConfigurationException, SAXException {
        Flux<CompanyDto> companies = companyService.readCompanyFile();

        List<ArticleDto> articleRecordFlux = articleService.readArticleLines("data/articles");
        return articleRecordFlux;
    }
}
