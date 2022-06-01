//package com.swipejobs.jobsearcher.cache.mongo;
//
//import com.swipejobs.jobsearcher.cache.WorkerJobCache;
//import com.swipejobs.jobsearcher.cache.mongo.model.WorkerJobs;
//import com.swipejobs.jobsearcher.cache.mongo.repository.WorkerJobRepository;
//import com.swipejobs.jobsearcher.model.api.WorkerJob;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@ConditionalOnProperty(
//        value="job-searcher.cache.type",
//        havingValue = "MONGO",
//        matchIfMissing = false)
//public class MongoWorkerJobCache implements WorkerJobCache {
//
//    @Autowired
//    private WorkerJobRepository workerJobRepository;
//
//    @Override
//    public Optional<List<WorkerJob>> getJobs(String workerId) {
//        Optional<WorkerJobs> workerJobs = workerJobRepository.findByUserId(workerId);
//        if (workerJobs.isPresent()) {
//            return Optional.of(workerJobs.get().getJobs());
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public void cacheJobs(String workerId, List<WorkerJob> jobs) {
//        WorkerJobs workerJobs = WorkerJobs
//                .builder()
//                .userId(workerId)
//                .jobs(jobs)
//                .build();
//        workerJobRepository.save(workerJobs);
//    }
//}
