package com.swipejobs.jobsearcher.integration.worker;

import com.swipejobs.jobsearcher.configuration.ApiConfiguration;
import com.swipejobs.jobsearcher.integration.AbstractWebService;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class WorkerWebService extends AbstractWebService {

    @Autowired
    public WorkerWebService(WebClient.Builder builder, ApiConfiguration apiConfiguration) {
        super(builder, apiConfiguration);
    }

    public Flux<Worker> getWorkers(){
        return webClient.get().uri(apiConfiguration.getPaths().getWorkers())
                .retrieve()
                .bodyToFlux(Worker.class);
    }
}
