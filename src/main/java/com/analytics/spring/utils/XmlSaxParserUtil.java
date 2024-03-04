package com.analytics.spring.utils;

import com.analytics.spring.dto.Article;
import lombok.Getter;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSaxParserUtil extends DefaultHandler {
    private static final String TEXT = "text";
    private static final String TITLE = "title";

    @Getter
    @ThreadSafe
    private Article article;

    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue != null) {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        article = new Article();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case TEXT, TITLE:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case TEXT:
                article.setContent(elementValue.toString().trim());
                break;
            case TITLE:
                article.setTitle(elementValue.toString().trim());
                break;
        }
    }
}
