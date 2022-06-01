package com.swipejobs.jobsearcher.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "job-searcher.integration.api")
@Data
public class ApiConfiguration {

    private String baseUrl;

    private Paths paths;

    @Data
    public static class Paths {

        private String jobs;

        private String workers;
    }
}
