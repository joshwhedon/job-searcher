//package com.swipejobs.jobsearcher.cache.mongo;
//
//import com.swipejobs.jobsearcher.cache.WorkerCache;
//import com.swipejobs.jobsearcher.cache.mongo.repository.WorkerRepository;
//import com.swipejobs.jobsearcher.integration.worker.model.Worker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//@ConditionalOnProperty(
//        value="job-searcher.cache.type",
//        havingValue = "MONGO",
//        matchIfMissing = false)
//public class MongoWorkerCache implements WorkerCache {
//
//    @Autowired
//    private WorkerRepository workerRepository;
//
//    @Override
//    public Optional<Worker> getWorker(String workerId) {
//        return workerRepository.findByUserId(workerId);
//    }
//
//    @Override
//    public void cacheWorker(Worker worker) {
//        workerRepository.save(worker);
//    }
//}
