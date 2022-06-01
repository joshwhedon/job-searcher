package com.swipejobs.jobsearcher.cache;

import com.swipejobs.jobsearcher.integration.worker.model.Worker;

import java.util.Optional;

public interface WorkerCache {

    Optional<Worker> getWorker(String workerId);

    void cacheWorker(Worker worker);
}
