package com.analytics.spring.service.impl;

import com.analytics.spring.dto.ArticleDto;
import com.analytics.spring.service.IArticleService;
import com.analytics.spring.utils.XmlSaxParserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.analytics.spring.utils.FileUtil.getAllFilesInFolder;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Value("${articles_path}")
    private String articlesPath;
    @Override
    public List<ArticleDto> readArticleLines(String folderPath) throws IOException {
        File[] files = getAllFilesInFolder(folderPath);
        List<ArticleDto> articleRecordFlux = new ArrayList<>();

        for (File file: files) {
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                XmlSaxParserUtil handler = new XmlSaxParserUtil();

                saxParser.parse(file.getPath(), handler);

                ArticleDto result = handler.getArticleRecord();
                articleRecordFlux.add(result);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new IOException(e);
            }
        }
        return articleRecordFlux;
    }
}
