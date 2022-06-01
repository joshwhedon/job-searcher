package com.swipejobs.jobsearcher.cache.memory;

import com.swipejobs.jobsearcher.cache.WorkerJobCache;
import com.swipejobs.jobsearcher.model.api.WorkerJob;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(
        value="job-searcher.cache.type",
        havingValue = "MEMORY",
        matchIfMissing = true)
public class InMemoryWorkerJobCache implements WorkerJobCache {

    private Map<String, List<WorkerJob>> workerJobs = new HashMap<>();

    @Override
    public Optional<List<WorkerJob>> getJobs(String workerId) {
        return Optional.ofNullable(workerJobs.get(workerId));
    }

    @Override
    public void cacheJobs(String workerId, List<WorkerJob> jobs) {
        workerJobs.put(workerId, jobs);
    }
}
