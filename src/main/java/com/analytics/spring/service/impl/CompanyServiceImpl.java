package com.analytics.spring.service.impl;

import com.analytics.spring.dto.CompanyDto;
import com.analytics.spring.service.ICompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Value("${delimiter}")
    private String delimiter;

    @Override
    public Flux<CompanyDto> readCompanyFile() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/company_list.csv");
        Stream<String> linesStream = Files.lines(Paths.get(file.getPath())).skip(1);

        return Flux.fromStream(linesStream)
                .map(line -> new CompanyDto(line.split(delimiter)[1]))
                .publishOn(Schedulers.parallel());
    }
}
