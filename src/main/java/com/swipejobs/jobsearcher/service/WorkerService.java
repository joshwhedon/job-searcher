package com.swipejobs.jobsearcher.service;

import com.swipejobs.jobsearcher.cache.WorkerCache;
import com.swipejobs.jobsearcher.integration.worker.WorkerWebService;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.WorkerParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerWebService workerWebService;

    @Autowired
    private WorkerCache workerCache;

    public Optional<Worker> getWorker(WorkerParameters workerParameters) {
        Optional<Worker> optionalWorker = workerCache.getWorker(workerParameters.getWorkerId());
        if (optionalWorker.isPresent()) {
            return optionalWorker;
        }
        return getWorkerFromService(workerParameters);
    }

    private Optional<Worker> getWorkerFromService(WorkerParameters workerParameters) {
        Flux<Worker> workerFlux = workerWebService.getWorkers();
        Optional<Worker> optionalWorker = workerFlux
                .toStream()
                .filter(worker -> workerParameters.getWorkerId().equals(worker.getUserId()))
                .findFirst();
        if (optionalWorker.isPresent()) {
            // We fill the cache when worker is found instead of on initialisation because not all workers will be needed. Ideally the api would allow retrieving a worker by id
            workerCache.cacheWorker(optionalWorker.get());
        }
        return optionalWorker;
    }
}
