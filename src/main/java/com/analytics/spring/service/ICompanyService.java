package com.analytics.spring.service;

import com.analytics.spring.dto.Company;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface ICompanyService {

    Flux<Company> processCompanies() throws IOException;
}
