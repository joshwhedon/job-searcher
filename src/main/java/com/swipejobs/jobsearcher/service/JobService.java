package com.swipejobs.jobsearcher.service;

import com.swipejobs.jobsearcher.cache.JobCache;
import com.swipejobs.jobsearcher.integration.job.JobWebService;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService implements InitializingBean {

    @Autowired
    private JobCache jobCache;

    @Autowired
    private JobWebService jobWebService;

    public List<Job> getJobs() {
        return jobCache.getJobs();
    }

    // We fill the cache on initialisation because all jobs will be needed for every worker
    @Override
    public void afterPropertiesSet() throws Exception {
        Flux<Job> jobFlux = jobWebService.getJobs();
        jobCache.cacheJobs(
                jobFlux
                        .collectList()
                        .block()
                        .stream()
                        .filter(job -> job.getWorkersRequired() > 0)
                        .collect(Collectors.toList()));
    }
}
