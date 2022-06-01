package com.swipejobs.jobsearcher.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "job-searcher.business")
@Data
public class BusinessConfiguration {

    private int maxJobs;
}
