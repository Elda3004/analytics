package com.analytics.spring.service;

import com.analytics.spring.dto.CompanyDto;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface ICompanyService {

    Flux<CompanyDto> readCompanyFile() throws IOException;
}
