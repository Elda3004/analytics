package com.analytics.spring.service;

import com.analytics.spring.dto.ArticleDto;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface IArticleService {

    List<ArticleDto> readArticleLines(String folderPath) throws IOException, ParserConfigurationException, SAXException;
}
