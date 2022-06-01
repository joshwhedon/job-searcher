//package com.swipejobs.jobsearcher.cache.mongo;
//
//import com.swipejobs.jobsearcher.cache.JobCache;
//import com.swipejobs.jobsearcher.cache.mongo.repository.JobRepository;
//import com.swipejobs.jobsearcher.integration.job.model.Job;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@ConditionalOnProperty(
//        value="job-searcher.cache.type",
//        havingValue = "MONGO",
//        matchIfMissing = false)
//public class MongoJobCache implements JobCache {
//
//    @Autowired
//    private JobRepository jobRepository;
//
//    @Override
//    public List<Job> getJobs() {
//        return jobRepository.findAll();
//    }
//
//    @Override
//    public void cacheJobs(List<Job> jobs) {
//        jobRepository.deleteAll();
//        jobRepository.saveAll(jobs);
//    }
//}
