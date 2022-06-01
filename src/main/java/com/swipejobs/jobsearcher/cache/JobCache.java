package com.swipejobs.jobsearcher.cache;

import com.swipejobs.jobsearcher.integration.job.model.Job;

import java.util.List;

public interface JobCache {

    List<Job> getJobs();

    void cacheJobs(List<Job> jobs);
}
