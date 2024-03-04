package com.analytics.spring.commands;

import com.analytics.spring.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;

@ShellComponent
public class ProcessCompaniesCommand {

    @Autowired
    private ICompanyService companyService;

    @ShellMethod(value = "Read csv file of companies and cache results", key = "read-companies")
    public void processCompaniesFile() throws IOException {
        try {
            companyService.processCompanies();
        } catch (IOException e) {
            throw new IOException("Could not process csv file of companies", e);
        }
    }
}
