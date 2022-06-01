package com.swipejobs.jobsearcher.cache;

import com.swipejobs.jobsearcher.model.api.WorkerJob;

import java.util.List;
import java.util.Optional;

public interface WorkerJobCache {

    Optional<List<WorkerJob>> getJobs(String workerId);

    void cacheJobs(String workerId, List<WorkerJob> workerJobs);
}
