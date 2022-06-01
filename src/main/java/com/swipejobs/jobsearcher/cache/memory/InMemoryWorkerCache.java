package com.swipejobs.jobsearcher.cache.memory;

import com.swipejobs.jobsearcher.cache.WorkerCache;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(
        value="job-searcher.cache.type",
        havingValue = "MEMORY",
        matchIfMissing = true)
public class InMemoryWorkerCache implements WorkerCache {

    private Map<String, Worker> workers = new HashMap<>();

    @Override
    public Optional<Worker> getWorker(String workerId) {
        return Optional.ofNullable(workers.get(workerId));
    }

    @Override
    public void cacheWorker(Worker worker) {
        workers.put(worker.getUserId(), worker);
    }
}
