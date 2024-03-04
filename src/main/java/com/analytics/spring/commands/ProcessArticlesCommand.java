package com.analytics.spring.commands;

import com.analytics.spring.service.IArticleReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;

@ShellComponent
public class ProcessArticlesCommand {

    @Autowired
    private IArticleReaderService articleReaderService;

    @ShellMethod(value = "Read xml files of articles and cache results", key = "read-articles")
    public void processCompaniesFile() throws IOException {
        try {
            articleReaderService.processArticles();
        } catch (IOException exception) {
            throw new IOException("Error while processing xml files",exception);
        }
    }
}
