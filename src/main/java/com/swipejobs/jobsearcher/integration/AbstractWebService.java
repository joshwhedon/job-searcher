package com.swipejobs.jobsearcher.integration;

import com.swipejobs.jobsearcher.configuration.ApiConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractWebService {

    protected ApiConfiguration apiConfiguration;

    protected final WebClient webClient;

    @Autowired
    public AbstractWebService(WebClient.Builder builder, ApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
        this.webClient = builder
                .baseUrl(apiConfiguration.getBaseUrl())
                .build();
    }
}
