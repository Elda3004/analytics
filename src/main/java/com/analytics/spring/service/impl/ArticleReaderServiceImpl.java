package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Article;
import com.analytics.spring.service.IArticleReaderService;
import com.analytics.spring.utils.XmlSaxParserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ArticleReaderServiceImpl implements IArticleReaderService {
    @Value("${articles_path}")
    private String articlesPath;

    @Cacheable("articles")
    public Flux<Article> processArticles() {
        return Flux.defer(() -> {
            try {
                // Load XML files from the folder
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources("classpath:"+articlesPath+"/*.xml");
                return Flux.fromArray(resources)
                        .parallel()
                        .runOn(Schedulers.parallel())
                        .flatMap(resource -> {
                            Mono<Article> articleDtoMono = readAndParseXML(resource);
                            return Flux.merge(articleDtoMono);
                        })
                        .sequential()
                        .cache();
            } catch (IOException e) {
                return Flux.error(e);
            }
        });
    }

    private Mono<Article> readAndParseXML(Resource resource) {
        return Mono.fromCallable(() -> {
            try (InputStream inputStream = resource.getInputStream()) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                XmlSaxParserUtil handler = new XmlSaxParserUtil();
                saxParser.parse(inputStream, handler);

                return handler.getArticle();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException("Error reading or parsing XML file", e);
            }
        });
    }
}
