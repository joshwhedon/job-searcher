package com.swipejobs.jobsearcher.cache.memory;

import com.swipejobs.jobsearcher.cache.JobCache;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        value="job-searcher.cache.type",
        havingValue = "MEMORY",
        matchIfMissing = true)
public class InMemoryJobCache implements JobCache {

    List<Job> jobs;

    @Override
    public List<Job> getJobs() {
        return jobs;
    }

    @Override
    public void cacheJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
