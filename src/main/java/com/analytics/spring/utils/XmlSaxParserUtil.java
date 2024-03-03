package com.analytics.spring.utils;

import com.analytics.spring.dto.ArticleDto;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSaxParserUtil extends DefaultHandler {
    private static final String TEXT = "text";
    @Getter
    private ArticleDto articleRecord;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = elementValue == null ? new StringBuilder() : elementValue.append(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {
        articleRecord = new ArticleDto();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        if (qName.equalsIgnoreCase(TEXT)) {
            elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(TEXT)) {
            articleRecord.setContent(elementValue.toString());
        }
    }
}
