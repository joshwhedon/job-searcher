package com.swipejobs.jobsearcher.integration.job;

import com.swipejobs.jobsearcher.configuration.ApiConfiguration;
import com.swipejobs.jobsearcher.integration.AbstractWebService;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class JobWebService extends AbstractWebService {

    public JobWebService(WebClient.Builder builder, ApiConfiguration apiConfiguration) {
        super(builder, apiConfiguration);
    }

    public Flux<Job> getJobs(){
        return webClient.get().uri(apiConfiguration.getPaths().getJobs())
                .retrieve()
                .bodyToFlux(Job.class);
    }
}
