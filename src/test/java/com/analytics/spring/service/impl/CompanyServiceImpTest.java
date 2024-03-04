package com.analytics.spring.service.impl;

import com.analytics.spring.dto.Company;
import com.analytics.spring.service.ICompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CompanyServiceImpTest {

    @MockBean
    private ICompanyService companyService;

    @Test
    public void testReadAllCompaniesFromCSVFIle() throws IOException {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("European Bank"));
        companies.add(new Company("York"));

        // Stub methods in mock services
        when(companyService.processCompanies()).thenReturn(Flux.fromIterable(companies));

        // Call the method to test
        Flux<Company> resultFlux = companyService.processCompanies();

        // Verify the results using StepVerifier
        StepVerifier.create(resultFlux)
                .expectNextMatches(record -> record.getName().equals("European Bank"))
                .expectNextMatches(record -> record.getName().equals("York"))
                .expectComplete()
                .verify();

        verify(companyService, times(1)).processCompanies();
    }
}
